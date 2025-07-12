package mg.itu.biblio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.itu.biblio.models.Livre;

import mg.itu.biblio.repositories.LivreRepository;

@Service
public class LivreService {
    
    @Autowired
    private LivreRepository livreRepository;



    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }
    public Livre getLivreById(Integer id) {
        return livreRepository.findById(id).orElse(null);
    }
    
}
