package mg.itu.biblio.controllers;


import org.springframework.ui.Model;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mg.itu.biblio.models.Adhesion;
import mg.itu.biblio.models.AdhesionType;
import mg.itu.biblio.models.Utilisateur;
import mg.itu.biblio.models.UtilisateurType;
import mg.itu.biblio.services.UtilisateurService;

@Controller
@RequestMapping("/sign")
public class AuthentificationController {
    
    @Autowired
    private UtilisateurService utilisateurService;


    @GetMapping("/up")
    public String signUp(
        
    @RequestParam(value = "error", required = false) String error,
    @RequestParam(value = "success" , required = false) String success,
    Model model) {
        if (error!=null && error.equals("empty_fields")) {
            model.addAttribute("error", "Tous les champs sont requis.");
        } else if (error!=null && error.equals("email_exists")) {
            model.addAttribute("error", "L'email est déjà utilisé.");
        } else {
            model.addAttribute("error", null);
        }
        if (success != null && success.equals("true")) {
            model.addAttribute("success", "Inscription réussie. Vous pouvez vous connecter.");
        } else {
            model.addAttribute("success", null);
        }
        return "signUp"; 
    }

    @PostMapping("/up")
    public String signUp(
        @RequestParam("nom") String nom,
        @RequestParam("prenom") String prenom,
        @RequestParam("email") String email,
        @RequestParam("mdp") String mdp,
        @RequestParam("dateNaissance") String dateNaissance
        ){
        // Vérification des champs requis
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty() || dateNaissance.isEmpty()) {
            return "redirect:/sign/up?error=empty_fields";
        }
        // Vérification de l'unicité de l'email
        if (utilisateurService.findByEmail(email) != null) {
            return "redirect:/sign/up?error=email_exists";
        }
        LocalDate dateNaissanceParsed = LocalDate.parse(dateNaissance);
        UtilisateurType utilisateurType = utilisateurService.getTypeUtilisateur();
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setEmail(email);
        utilisateur.setMotDePasse(mdp);
        utilisateur.setDateNaissance(dateNaissanceParsed);
        utilisateur.setTypeUtilisateur(utilisateurType);
        utilisateur.setDateInscription(LocalDate.now());

        utilisateurService.saveUtilisateur(utilisateur);

        return "redirect:/sign/up?success=true";

    }

    @GetMapping("/in")
    public String signIn(
        @RequestParam(value = "error", required = false) String error,
        Model model) {
        if (error != null && error.equals("invalid_credentials")) {
            model.addAttribute("error", "Identifiants invalides. Veuillez réessayer.");
        } else if(error != null && error.equals("connection_need")) {
            model.addAttribute("error", "Vous devez vous connecter pour accéder à cette page.");
        } else {
            model.addAttribute("error", null);
        }
        return "signIn"; 
    }
    @PostMapping("/in")
    public String signIn(
        @RequestParam("email") String email,
        @RequestParam("mdp") String mdp,
        HttpSession session
        ) {
        
        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmailAndMotDePasse(email, mdp);
        
        if (utilisateur == null) {
            return "redirect:/sign/in?error=invalid_credentials";
        }
        
        session.setAttribute("utilisateur", utilisateur);

        session.setAttribute("utilisateurType", utilisateur.getTypeUtilisateur());
        
        return "redirect:/"; 
    }

    @GetMapping("/out")
    public String signOut(HttpSession session) {
        session.invalidate();
        return "redirect:/"; 
    }

    @GetMapping("/adhesion")
    public String adhesion(
        @RequestParam(value = "error", required = false) String error,
        Model model) {
        if (error != null && error.equals("already_member")) {
            model.addAttribute("error", "Vous êtes déjà membre.");
        } else if(error != null && error.equals("invalid_adhesion_type")){
            model.addAttribute("error", "Type d'adhésion invalide.");
        } else if(error != null && error.equals("invalid_email")) {
            model.addAttribute("error", "Email invalide.");
        } else if(error != null && error.equals("not_enough_permissions")) {
            model.addAttribute("error", "Vous n'avez pas les permissions nécessaires pour effectuer cette action.");
        } else {
            model.addAttribute("error", null);
        }
        model.addAttribute("adhesions", utilisateurService.getAllAdhesionTypes());

        return "adhesion";
    }

    @PostMapping("/adhesion")
    public String adhesion(
        @RequestParam("email") String email,
        @RequestParam("adhesionTypeId") Integer adhesionTypeId,
        @RequestParam("nbMois") Integer nbMois,
        HttpSession session) {
        
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur==null) {
            return "redirect:/sign/in?error=connection_need";
        }
        UtilisateurType utilisateurType = (UtilisateurType) session.getAttribute("utilisateurType");
        if (utilisateurType.getId().equals(2)){
            return "redirect:/sign/adhesion?error=not_enough_permissions";
        }

        AdhesionType adhesionType = utilisateurService.getAdhesionTypeById(adhesionTypeId);
        if (adhesionType == null) {
            return "redirect:/sign/adhesion?error=invalid_adhesion_type";
        }
        Utilisateur user = utilisateurService.findByEmail(email);
        if (user == null) {
            return "redirect:/sign/adhesion?error=invalid_email";
        }

        Adhesion lastAdhesion = utilisateurService.getLastAdhesionByUtilisateur(user);
        
        LocalDate now = LocalDate.now();
        Adhesion adhesion = new Adhesion();
        adhesion.setDateIn(now);
        if (lastAdhesion != null && lastAdhesion.getDateFin().isAfter(now)) {
            return "redirect:/sign/adhesion?error=already_member";
        } else {
            LocalDate dateFin = now.plusMonths(nbMois);
            adhesion.setDateFin(dateFin);
        }
        
        adhesion.setUtilisateur(user);
        adhesion.setTypeAdhesion(adhesionType);

        utilisateurService.saveAdhesion(adhesion);

        return "redirect:/"; 
    }
    


}
