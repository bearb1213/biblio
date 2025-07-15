package mg.itu.biblio.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.itu.biblio.models.Exemplaire;
import mg.itu.biblio.models.Livre;
import mg.itu.biblio.models.LivreExemplaire;
import mg.itu.biblio.models.ExemplaireDisponible;
import mg.itu.biblio.models.Genre;

import mg.itu.biblio.repositories.LivreRepository;
import mg.itu.biblio.repositories.ExemplaireRepository;



@Service
public class LivreService {
    
    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private PretService pretService;


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

    public LivreExemplaire getStateLivreExemplaire(Integer id) throws Exception{
        LivreExemplaire livreExemplaire = new LivreExemplaire();
        List<ExemplaireDisponible> exemplaireDisponibles = new ArrayList<>();
        LocalDate now = LocalDate.now();

        Livre livre = livreRepository.findById(id).orElse(null);
        if (livre == null) {
            throw new Exception("Livre innexistant");
        } 
        List<Genre> gs = livre.getGenres();
        for (Genre genre : gs) {
            genre.setLivres(null);
        }
        livreExemplaire.setLivre(livre);
        List<Exemplaire> exemplaires = exemplaireRepository.findByLivreId(id);
        for (Exemplaire exemplaire : exemplaires) {
            ExemplaireDisponible exemplaireDisponible = new ExemplaireDisponible();
            exemplaireDisponible.setExemplaireId(exemplaire.getId());
            if (pretService.isEnPret(exemplaire, now)) {
                exemplaireDisponible.setDisponibilite("En pret");
            } else {
                exemplaireDisponible.setDisponibilite("disponible");
            }
            exemplaireDisponibles.add(exemplaireDisponible);
        }
        livreExemplaire.setExemplaires(exemplaireDisponibles);
        return livreExemplaire;


    }
     
}
