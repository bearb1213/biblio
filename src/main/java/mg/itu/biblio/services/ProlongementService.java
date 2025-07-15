package mg.itu.biblio.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.itu.biblio.models.Adhesion;
import mg.itu.biblio.models.Pret;
import mg.itu.biblio.models.PretNbJour;
import mg.itu.biblio.models.PretStatus;
import mg.itu.biblio.models.Prolongement;
import mg.itu.biblio.models.Utilisateur;
import mg.itu.biblio.repositories.PretNbJourRepository;
import mg.itu.biblio.repositories.PretRepository;
import mg.itu.biblio.repositories.PretStatusRepository;
import mg.itu.biblio.repositories.ProlongementRepository;

@Service
public class ProlongementService {
    
    @Autowired
    ProlongementRepository prolongementRepository;

    @Autowired 
    UtilisateurService utilisateurService;

    @Autowired
    PretNbJourRepository pretNbJourRepository;

    @Autowired
    PretRepository pretRepository;

    @Autowired
    PretStatusRepository pretStatusRepository;


    public Prolongement findById(Integer id){
        return prolongementRepository.findById(id).orElse(null);
    }
    public Prolongement save(Prolongement prolongement){
        return prolongementRepository.save(prolongement);
    }

    /// tsy findBy status fa tokony last no alaina
    public List<Prolongement> findDemande(){
        return prolongementRepository.findLastProlongementByPret("DEMANDE");
    }
    

    public Prolongement createProlongement(Pret pret){
        Prolongement p = new Prolongement();
        p.setDateDemande(LocalDateTime.now());
        p.setStatut("DEMANDE");
        p.setPret(pret);
        return prolongementRepository.save(p);
    }


    public Prolongement refuser(Prolongement prolongement){
        Prolongement p = new Prolongement();
        p.setDateDemande(LocalDateTime.now());
        p.setStatut("REFUSE");
        p.setPret(prolongement.getPret());
        return prolongementRepository.save(p);
    }
    public Prolongement refuser(Pret pret){
        Prolongement p = new Prolongement();
        p.setDateDemande(LocalDateTime.now());
        p.setStatut("REFUSE");
        p.setPret(pret);
        return prolongementRepository.save(p);
    }
    

    public Prolongement accepter(Prolongement prolongement , Adhesion adhesion){
        LocalDateTime now = LocalDateTime.now();
        Prolongement p = new Prolongement();
        p.setDateDemande(now);
        p.setStatut("ACCEPTE");
        p.setPret(prolongement.getPret());

        PretNbJour pretNbJour = pretNbJourRepository.findByAdhesionTypeId(adhesion.getId()).orElse(null);
        

        Pret pret = prolongement.getPret();
        LocalDate debut = pret.getDateRetourPrevue().minusDays(pretNbJour.getNbJour());
        pret.setDateRetourPrevue(pret.getDateRetourPrevue().plusDays(pretNbJour.getNbJour()));
        pret.setStatut("PROLONGE");
        
        PretStatus pretStatus = new PretStatus();
        pretStatus.setStatu("PROLONGE");
        pretStatus.setDateIn(now);
        pretStatus.setDateDebut(debut);
        pretStatus.setDateFin(pret.getDateRetourPrevue());
        pretStatus.setPret(pret);

        
        pretRepository.save(pret);

        pretStatusRepository.save(pretStatus);
        
        return prolongementRepository.save(p);
    }



}
