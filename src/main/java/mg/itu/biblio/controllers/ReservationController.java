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
import mg.itu.biblio.models.Utilisateur;
import mg.itu.biblio.models.UtilisateurType;
import mg.itu.biblio.models.Adhesion;
import mg.itu.biblio.models.Exemplaire;
import mg.itu.biblio.models.Livre;
import mg.itu.biblio.models.Reservation;
import mg.itu.biblio.services.LivreService;
import mg.itu.biblio.services.ReservationService;
import mg.itu.biblio.services.UtilisateurService;

@Controller
@RequestMapping("/reservation")
public class ReservationController {
    
    
    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ReservationService reservationService;

    @Autowired 
    private LivreService livreService;

    @PostMapping("/new")
    public String createReservation(
        @RequestParam("livreId") Integer livreId,
        @RequestParam("exemplaireId") Integer exemplaireId,
        @RequestParam("dateReservation") String dateReservation,
        @RequestParam("motif") String motif ,
        HttpSession session
    ){
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur == null) {
            return "redirect:/sign/in?error=connection_need";
        } 
        UtilisateurType utilisateurType = (UtilisateurType) session.getAttribute("utilisateurType");
        if (utilisateurType == null) {
            return "redirect:/sign/in?error=connection_need";
        } else if( utilisateurType.getId() != 1 && utilisateurType.getId() != 2) {
            return "redirect:/sign/in?error=access_denied";
        } 
        Livre livre = livreService.getLivreById(livreId);
        if(livre == null){
            return "redirect:/?error=livre_not_found";
        }
        Exemplaire exemplaire = livreService.getExemplaireById(exemplaireId);
        if(exemplaire == null){
            return "redirect:/?error=exemplaire_not_found";
        }
        if (dateReservation == null || dateReservation.isEmpty()) {
            return "redirect:/?error=reservation_error";
        }
        Adhesion adhesion = utilisateurService.isAdherant(utilisateur);
        if (adhesion==null) {
            return "redirect:/?error=adhesion_error";
        }
        if (reservationService.getNbReservation(utilisateur) >= reservationService.getReservationQuota(adhesion.getTypeAdhesion().getId()) ) {
            return "redirect:/?error=reservation_nb";
        }
        
        LocalDate dateReservationParsed = LocalDate.parse(dateReservation);
        try {
            reservationService.createReservation(dateReservationParsed, motif, utilisateur, exemplaire);
        } catch (Exception e) {
            return "redirect:/?error=reservation_error";
        }
        
        return "redirect:/livres/"+livreId+"?success=reservation_created";
    }

    @GetMapping
    public String listEnAttente(
        @RequestParam(value = "error" , required = false) String error,
        @RequestParam(value = "success" , required = false) String success,
        HttpSession session , 
        Model model 
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
        if (error != null) {
            switch (error) {
                case "reservation_not_found":
                    model.addAttribute("error", "Reservation non trouve.");
                    break;
            
                default:
                    model.addAttribute("error", "Probleme est survenu lors de l'action.");
                    break;
            }
        } else {
            model.addAttribute("error", null);
        }
        if (success != null) {
            switch (success) {
                case "reservation_accepted":
                    model.addAttribute("success", "Reservation accepter avec success.");
                    break;
                case "reservation_refuser":
                    model.addAttribute("success", "Reservation refuser avec success.");
                    break;
                
                default:
                    model.addAttribute("success", "Probleme est survenu lors de l'action.");
                    break;
            }
        } else {
            model.addAttribute("success", null);
        }
        



        model.addAttribute("reservation", reservationService.findAllReservationByStatut("EN_ATTENTE"));
        return "reservation";
    }

    @PostMapping("/{id}/confirm")
    public String accepter(
        @PathVariable("id") Integer id,
        HttpSession session 
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
        try {
            Reservation reservation = reservationService.findById(id);
            if (reservation==null) {
                return "redirect:/reservation?error=reservation_not_found";
            }
            reservationService.accepteReservation(reservation);
            return "redirect:/reservation?success=reservation_accepted";

        } catch (Exception e) {
            return "redirect:/reservation?error=error";
        }
    }

    @PostMapping("/{id}/reject")
    public String refuser(
        @PathVariable("id") Integer id,
        HttpSession session 
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
        try {
            Reservation reservation = reservationService.findById(id);
            if (reservation==null) {
                return "redirect:/reservation?error=reservation_not_found";
            }
            reservationService.refuseReservation(reservation);
            return "redirect:/reservation?success=reservation_refuser";

        } catch (Exception e) {
            return "redirect:/reservation?error=error";
        }
    }

    



}
