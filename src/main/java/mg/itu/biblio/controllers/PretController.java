package mg.itu.biblio.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import mg.itu.biblio.models.Adhesion;
import mg.itu.biblio.models.Exemplaire;
import mg.itu.biblio.models.Livre;
import mg.itu.biblio.models.Pret;
import mg.itu.biblio.models.Utilisateur;
import mg.itu.biblio.models.UtilisateurType;
import mg.itu.biblio.services.LivreService;
import mg.itu.biblio.services.PretService;
import mg.itu.biblio.services.UtilisateurService;

@Controller
@RequestMapping("/pret")
public class PretController {
    
    @Autowired
    private PretService pretService;

    @Autowired 
    private LivreService livreService;

    @Autowired
    private UtilisateurService utilisateurService;

    

    @PostMapping("/new")
    public String preter(
        @RequestParam("livreId") Integer livreId,
        @RequestParam("utilisateurId") Integer utilisateurId,
        @RequestParam("exemplaireId") Integer exemplaireId,
        @RequestParam("datePret") String datePret,
        HttpSession session
    ) {
        /// test user
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur == null) {
            return "redirect:/sign/in?error=connection_need";
        } 
        UtilisateurType utilisateurType = (UtilisateurType) session.getAttribute("utilisateurType");
        if (utilisateurType == null) {
            return "redirect:/sign/in?error=connection_need";
        } else if( utilisateurType.getId() != 1) {
            return "redirect:/sign/in?error=access_denied";
        } 

        /// test livre
        Livre livre = livreService.getLivreById(livreId);
        if(livre == null){
            return "redirect:/?error=livre_not_found";
        }
        Exemplaire exemplaire = livreService.getExemplaireById(exemplaireId);
        if(exemplaire == null){
            return "redirect:/?error=exemplaire_not_found";
        }

        Utilisateur user = utilisateurService.getUtilisateurById(utilisateurId);
        if (user == null ) {
            return "redirect:/?error=user_not_found";
        }
        Adhesion adhesion = utilisateurService.isAdherant(user);
        if (adhesion==null) {
            return "redirect:/?error=adhesion_error";
        }
        LocalDate datePretParsed ;
        if (datePret == null || datePret.isEmpty()) {
            datePretParsed = LocalDate.now();
        } else {
            datePretParsed = LocalDate.parse(datePret);
        }
        if (pretService.isEnPret(exemplaire, datePretParsed)) {
            return "redirect:/livres/"+livreId+"?error=exemplaire_used";
        }
        if (pretService.isPenaliser(user, datePretParsed)) {
            return "redirect:/?error=penalite";
        }




        
        
        if(pretService.getNbPret(user) >= pretService.getPretQuota(adhesion.getTypeAdhesion().getId())){
            return "redirect:/?error=pret_nb";
        }
        try {
            pretService.createPret(user, exemplaire, datePretParsed, adhesion.getTypeAdhesion());
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/?error=pret_error";
        }

        return "redirect:/livres/"+livreId+"?success=pret_created";
    }

    @GetMapping
    public String listePret(
    Model model,
    HttpSession session
    ){
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur == null) {
            return "redirect:/sign/in?error=connection_need";
        } 
        UtilisateurType utilisateurType = (UtilisateurType) session.getAttribute("utilisateurType");
        if (utilisateurType == null) {
            return "redirect:/sign/in?error=connection_need";
        } else if( utilisateurType.getId() != 1) {
            return "redirect:/sign/in?error=access_denied";
        } 

        model.addAttribute("enCours", pretService.listPretEnCours());
        model.addAttribute("prolonge", pretService.listPretProlonger());

        return "listePret";
    }

    @PostMapping("/rendre/{id}")
    public String rendre(
        HttpSession session ,
        @RequestParam("dateRetour") String dateRetour,
        @PathVariable("id") Integer id
    ) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur == null) {
            return "redirect:/sign/in?error=connection_need";
        } 
        UtilisateurType utilisateurType = (UtilisateurType) session.getAttribute("utilisateurType");
        if (utilisateurType == null) {
            return "redirect:/sign/in?error=connection_need";
        } else if( utilisateurType.getId() != 1) {
            return "redirect:/sign/in?error=access_denied";
        }
        Pret pret = pretService.findById(id);
        if (pret==null) {
            return "redirect:/?error=pret_not_found";
        }
        boolean penalite = pretService.rendre(pret, LocalDate.parse(dateRetour));
        if (penalite) {
            return "redirect:/livres/"+pret.getExemplaire().getId()+"?success=rendu_pret";
        }
        return "redirect:/?error=penalite_add";

    }

    @GetMapping("/mine")
    public String listPretMine(
        Model model,
        HttpSession session
    ) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur == null) {
            return "redirect:/sign/in?error=connection_need";
        } 
        UtilisateurType utilisateurType = (UtilisateurType) session.getAttribute("utilisateurType");
        if (utilisateurType == null) {
            return "redirect:/sign/in?error=connection_need";
        } 

        model.addAttribute("enCours", pretService.listPretEnCours(utilisateur));
        model.addAttribute("prolonge", pretService.listPretProlonger(utilisateur));

        return "listePretMine";
    }


}
