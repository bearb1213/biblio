package mg.itu.biblio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.itu.biblio.models.Exemplaire;
import mg.itu.biblio.models.Livre;

import mg.itu.biblio.repositories.LivreRepository;
import mg.itu.biblio.repositories.ExemplaireRepository;

@Service
public class LivreService {
    
    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;



    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }
    public Livre getLivreById(Integer id) {
        return livreRepository.findById(id).orElse(null);
    }
    public Exemplaire getExemplaireById(Integer id) {
        return exemplaireRepository.findById(id).orElse(null);
    }
    public List<Exemplaire> findExemplaireByLivreId(Integer id) throws Exception{
        Livre livre = livreRepository.findById(id).orElse(null);
        if (livre != null) {
            return exemplaireRepository.findByLivreId(id);
        }
        throw new Exception("Livre innexistant");
    }
     
}
