package mg.itu.biblio.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.itu.biblio.models.Pret;
import mg.itu.biblio.models.Prolongement;
import mg.itu.biblio.repositories.ProlongementRepository;

@Service
public class ProlongementService {
    
    @Autowired
    ProlongementRepository prolongementRepository;


    public Prolongement findById(Integer id){
        return prolongementRepository.findById(id).orElse(null);
    }
    public Prolongement save(Prolongement prolongement){
        return prolongementRepository.save(prolongement);
    }

    /// tsy findBy status fa tokony last no alaina
    public List<Prolongement> findDemande(){
        return prolongementRepository.findByStatut("DEMANDE");
    }
    

    public Prolongement createProlongement(Pret pret){
        Prolongement p = new Prolongement();
        p.setDateDemande(LocalDateTime.now());
        p.setStatut("DEMANDE");
        p.setPret(pret);
        return prolongementRepository.save(p);
    }


    // tokony pret no etooo
    public Prolongement refuser(Prolongement prolongement){
        Prolongement p = new Prolongement();
        p.setDateDemande(LocalDateTime.now());
        p.setStatut("REFUSE");
        p.setPret(prolongement.getPret());
        return prolongementRepository.save(p);
    }

    



}
