package mg.itu.biblio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.itu.biblio.models.LivreExemplaire;
import mg.itu.biblio.services.LivreService;

@Controller
@RequestMapping("/api")
@RestController
public class WsController {
    
    @Autowired
    private LivreService livreService;

    @GetMapping("/livre/{id}")
    public LivreExemplaire info(
        @PathVariable("id") Integer id
    ) {
        try {
            return livreService.getStateLivreExemplaire(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
