package mg.itu.biblio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import mg.itu.biblio.models.Pret;
import mg.itu.biblio.models.Utilisateur;
import mg.itu.biblio.models.UtilisateurType;
import mg.itu.biblio.services.PretService;
import mg.itu.biblio.services.ProlongementService;

@Controller
@RequestMapping("/prolongement")
public class ProlongementController {
    
    @Autowired
    private ProlongementService prolongementService;

    @Autowired 
    private PretService pretService;


    @PostMapping("/demande/{id}")
    public String demande(
        HttpSession session ,
        @PathVariable("id") Integer id
    ){
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur == null) {
            return "redirect:/sign/in?error=connection_need";
        } 
        UtilisateurType utilisateurType = (UtilisateurType) session.getAttribute("utilisateurType");
        if (utilisateurType == null) {
            return "redirect:/sign/in?error=connection_need";
        } 
        
        Pret pret = pretService.findById(id);
        prolongementService.createProlongement(pret);

        return "redirect:/livres/"+pret.getExemplaire().getLivre().getId()+"?success=prolongement";

    }

    @GetMapping
    public String liste(
        HttpSession session,
        Model model
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
        model.addAttribute("prolongement", prolongementService.findDemande());

        return "listeDemande";

    }
    @PostMapping
    public String refuser(
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
        
        return "redirect:/";
    }
    


}
