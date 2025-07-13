package mg.itu.biblio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import mg.itu.biblio.services.LivreService;
import mg.itu.biblio.services.UtilisateurService;
import mg.itu.biblio.models.Livre;

import java.util.List;

@Controller
@RequestMapping
public class LivreController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping()
    public String getAllLivres(
    @RequestParam(value = "error", required = false) String error,  
    Model model) {
        if (error!=null ) {
            switch (error) {
                case "livre_not_found":
                    model.addAttribute("error", "Livre non trouve.");
                    break;
                case "exemplaire_not_found":
                    model.addAttribute("error", "Exemplaire non trouve.");
                    break;
                case "access_denied":
                    model.addAttribute("error", "Acces refuse.");
                    break;
                case "connection_need":
                    model.addAttribute("error", "Connexion requise.");
                    break;
                case "reservation_error":
                    model.addAttribute("error", "Une erreur s'est produit lors de la reservation.");
                    break;
                case "adhesion_error":
                    model.addAttribute("error", "Votre adhesion est tombe a echeance.");
                    break;
                case "reservation_nb":
                    model.addAttribute("error", "Vous avez atteint votre quotas.");
                    break;
                default:
                    model.addAttribute("error", "Erreur inconnue.");
                    break;
                    
                }
        } else {
            model.addAttribute("error", null); 
        }

        List<Livre> livres = livreService.getAllLivres();
        model.addAttribute("livres", livres);
        return "livres"; 
    }
    @GetMapping("/livres/{id}")
    public String detailLivre(
    Model model, 
    @PathVariable("id") Integer id , 
    @RequestParam( value = "success" , required = false) String success
    ) {
        Livre livre = livreService.getLivreById(id);
        if (livre != null) {
            try {
                
                model.addAttribute("livre", livre);
                model.addAttribute("exemplairesDisponos", livreService.findExemplaireByLivreId(id));
                model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (success!=null) {
                switch (success) {
                    case "reservation_created":
                        model.addAttribute("success", "Reservation reussi avec success. ");
                        break;
                    default:
                        model.addAttribute("success", null);
                        break;
                }
            } else {
                model.addAttribute("success", null);
            }
            
            return "detailLivre"; 
        } else {
            return "redirect:/"; 
        }
    }
    


}
