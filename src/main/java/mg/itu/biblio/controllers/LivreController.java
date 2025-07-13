package mg.itu.biblio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String getAllLivres(Model model) {
        List<Livre> livres = livreService.getAllLivres();
        model.addAttribute("livres", livres);
        return "livres"; 
    }
    @GetMapping("/livres/{id}")
    public String detailLivre(Model model, @PathVariable("id") Integer id) {
        Livre livre = livreService.getLivreById(id);
        if (livre != null) {
            try {
                
                model.addAttribute("livre", livre);
                model.addAttribute("exemplairesDisponos", livreService.findExemplaireByLivreId(id));
                model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return "detailLivre"; 
        } else {
            return "redirect:/"; 
        }
    }
    



    /// a mettre dans une fonction
    // Livre livre = livreService.findById(id);
        
    //     model.addAttribute("livre", livre);
    //     model.addAttribute("exemplairesDisponos", exemplaireService.findDisponiblesByLivreId(id));
    //     model.addAttribute("utilisateurs", utilisateurService.findAll());
        
    //     return "detail-livre";
}
