package mg.itu.biblio.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import mg.itu.biblio.models.Adhesion;
import mg.itu.biblio.models.AdhesionType;
import mg.itu.biblio.models.Livre;
import mg.itu.biblio.models.Penalite;
import mg.itu.biblio.models.Genre;
import mg.itu.biblio.models.Pret;
import mg.itu.biblio.models.Utilisateur;
import mg.itu.biblio.models.UtilisateurAbonnement;
import mg.itu.biblio.models.UtilisateurType;
import mg.itu.biblio.repositories.AdhesionRepository;
import mg.itu.biblio.repositories.AdhesionTypeRepository;
import mg.itu.biblio.repositories.LivreRepository;
import mg.itu.biblio.repositories.PenaliteRepository;
import mg.itu.biblio.repositories.PretRepository;
import mg.itu.biblio.repositories.QuotaRepository;
import mg.itu.biblio.repositories.UtilisateurRepository;
import mg.itu.biblio.repositories.UtilisateurTypeRepository;

@Service
public class UtilisateurService {
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private UtilisateurTypeRepository utilisateurTypeRepository;

    @Autowired
    private AdhesionTypeRepository adhesionTypeRepository;

    @Autowired
    private AdhesionRepository adhesionRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private LivreRepository livreRepository;

    public Utilisateur getUtilisateurById(Integer id) {
        return utilisateurRepository.findById(id).orElse(null);
    }
    public Utilisateur getUtilisateurByEmailAndMotDePasse(String email,String mdp) {
        return utilisateurRepository.findByEmailAndMotDePasse(email,mdp).orElse(null);
    }

    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email).orElse(null);
    }

    public List<Utilisateur> getAllUtilisateurs() {
        UtilisateurType utilisateurType = utilisateurTypeRepository.findById(2).orElse(null);
        return utilisateurRepository.findByTypeUtilisateur(utilisateurType);
    }
    public UtilisateurType getTypeUtilisateur(){
        return utilisateurTypeRepository.findById(2).orElse(null);
    }
    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public List<AdhesionType> getAllAdhesionTypes() {
        return adhesionTypeRepository.findAll();
    }

    public AdhesionType getAdhesionTypeById(Integer id) {
        return adhesionTypeRepository.findById(id).orElse(null);
    }

    public Adhesion saveAdhesion(Adhesion adhesion){
        return adhesionRepository.save(adhesion);
    }

    public Adhesion getLastAdhesionByUtilisateur(Utilisateur utilisateur) {
        return adhesionRepository.findFirstByUtilisateurOrderByDateInDesc(utilisateur).orElse(null);
    }   

    public Adhesion isAdherant(Utilisateur utilisateur){
        Adhesion adhesion = getLastAdhesionByUtilisateur(utilisateur);
        if (adhesion==null) {
            return null;
        } 
        if (adhesion.getDateFin().isBefore(LocalDate.now())) {
            return null;
        } 
        return adhesion;
        
    }

    
    @Autowired
    private QuotaRepository quotaRepository;

    @Autowired
    private PretRepository pretRepository;

    @Autowired 
    private PenaliteRepository penaliteRepository;

    
    public List<Penalite> listPenalites(Utilisateur utilisateur,LocalDate date){
        return penaliteRepository.findPenalitesByUtilisateurAndDateRange(utilisateur,date);
    }

    public Integer getPretQuota(Integer typeAdhesionId){
        return quotaRepository.findNombreMaxByTypeAndAction(typeAdhesionId, "pret").orElse(0);
    }
    public Integer getProlongementQuota(Integer typeAdhesionId){
        return quotaRepository.findNombreMaxByTypeAndAction(typeAdhesionId, "prolongement").orElse(0);
    }

    public Integer getNbPret(Utilisateur utilisateur){
        return getNbProlongement(utilisateur) + pretRepository.countByUtilisateurAndStatut(utilisateur, "EN_COURS"); 
    }

    public Integer getNbProlongement(Utilisateur utilisateur){
        return pretRepository.countByUtilisateurAndStatut(utilisateur, "PROLONGE");
    }
    public List<Pret> listPretEnCours(){
        return pretRepository.findByStatut("EN_COURS");
    }


    public UtilisateurAbonnement getInfoUser(Integer id){
        LocalDate now = LocalDate.now();
        UtilisateurAbonnement ua = new UtilisateurAbonnement();
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElse(null);
        if (utilisateur == null) {
            return ua;
        }
        ua.setUtilisateur(utilisateur);
        Adhesion adhesion = isAdherant(utilisateur);        
        if (adhesion==null) {
            return ua;
        }

        adhesion.setUtilisateur(null);

        ua.setAdhesion(adhesion);
        List<Penalite> penalites = listPenalites(utilisateur,now);
        for (Penalite penalite : penalites) {
            penalite.setUtilisateur(null);
        }
        ua.setPenalites(penalites);
        List<Pret> prets = listPretEnCours();
        for (Pret pret : prets) {
            pret.setUtilisateur(null);
            Livre livre = livreRepository.findById(pret.getExemplaire().getId()).orElse(null);
            List<Genre> gs = livre.getGenres();
            for (Genre genre : gs) {
                genre.setLivres(null);
            }
            pret.getExemplaire().setLivre(livre); 
        }

        ua.setPret(prets);

        if(adhesion!=null){
            Integer quotaPret = getPretQuota(adhesion.getTypeAdhesion().getId()) - getNbPret(utilisateur);
            ua.setQuotaPret(quotaPret);

            Integer quotaProlongemet = getProlongementQuota(adhesion.getTypeAdhesion().getId()) - getNbProlongement(utilisateur);
            ua.setQuotaProlongement(quotaProlongemet);

            Integer quotaReservation = reservationService.getReservationQuota(adhesion.getTypeAdhesion().getId()) - reservationService.getNbReservation(utilisateur);
            ua.setQuotaReservation(quotaReservation);
        }

        return ua;

    }
    

}
