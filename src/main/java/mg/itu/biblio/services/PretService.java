package mg.itu.biblio.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.pattern.Util;
import mg.itu.biblio.models.Utilisateur;
import mg.itu.biblio.models.Adhesion;
import mg.itu.biblio.models.AdhesionType;
import mg.itu.biblio.models.Exemplaire;
import mg.itu.biblio.models.Jf;
import mg.itu.biblio.models.Penalite;
import mg.itu.biblio.models.Pret;
import mg.itu.biblio.models.PretNbJour;
import mg.itu.biblio.models.PretStatus;
import mg.itu.biblio.repositories.JfRepository;
import mg.itu.biblio.repositories.PenaliteRepository;
import mg.itu.biblio.repositories.PenaliteTypeRepository;
import mg.itu.biblio.repositories.PretNbJourRepository;
import mg.itu.biblio.repositories.PretRepository;
import mg.itu.biblio.repositories.PretStatusRepository;
import mg.itu.biblio.repositories.QuotaRepository;

@Service
public class PretService {
    
    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private QuotaRepository quotaRepository;

    @Autowired 
    private PretNbJourRepository pretNbJourRepository;

    @Autowired
    private PretStatusRepository pretStatusRepository;

    @Autowired 
    private PenaliteRepository penaliteRepository;

    @Autowired
    private PenaliteTypeRepository penaliteTypeRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired 
    private JfRepository jfRepository;


    public Pret findById(Integer id){
        return pretRepository.findById(id).orElse(null);
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

    public Pret createPret(Utilisateur utilisateur ,Exemplaire exemplaire , LocalDate datePret , AdhesionType adhesionType,boolean surPlace ) throws Exception{
        LocalDateTime now = LocalDateTime.now();
        Pret pret= new Pret();
        PretNbJour pretNbJour = pretNbJourRepository.findByAdhesionTypeId(adhesionType.getId()).orElse(null);
        if (pretNbJour == null) {
            throw new Exception("Pret Nb jour pas encore configurer");
        }
        
        pret.setDateIn(now);
        pret.setUtilisateur(utilisateur);
        pret.setExemplaire(exemplaire);
        pret.setStatut("EN_COURS");
        
        

        PretStatus pretStatus = new PretStatus();
        pretStatus.setStatu("EN_COURS");
        pretStatus.setDateIn(now);
        if (surPlace) {
            pretStatus.setDateDebut(datePret);
            pretStatus.setDateFin(datePret);
            pret.setType("SUR_PLACE");
            pret.setDateRetourPrevue(datePret);

        } else {
            LocalDate dateFin = datePret.plusDays(pretNbJour.getNbJour());
            dateFin=ajusteDay(dateFin);

            pretStatus.setDateDebut(datePret);
            pretStatus.setDateFin(dateFin);
            pret.setType("MAISON");
            pret.setDateRetourPrevue(dateFin);

        }

        pretStatus.setPret(pret);

        pretRepository.save(pret);

        pretStatusRepository.save(pretStatus);

        return pret;
    }

    public boolean isEnPret(Exemplaire exemplaire,LocalDate date){
        return pretStatusRepository.existsByExemplaireAndDateBetween(exemplaire.getId(), date);
    }
    public boolean isPenaliser(Utilisateur utilisateur,LocalDate date){
        List<Penalite> penalites = penaliteRepository.findPenalitesByUtilisateurAndDateRange(utilisateur,date);

        System.out.println("\n\n\n\n\n\n\n\n\nPENALITE : "+penalites.size()+"\n\n\n\n\n\n\n\n\n\n\n");
        return penalites.size()>0;
    }
    public List<Penalite> listPenalites(Utilisateur utilisateur,LocalDate date){
        return penaliteRepository.findPenalitesByUtilisateurAndDateRange(utilisateur,date);
    }
    public List<Pret> listPretEnCours(){
        return pretRepository.findByStatut("EN_COURS");
    }
    public List<Pret> listPretProlonger(){
        return pretRepository.findByStatut("PROLONGE");
    }
    public boolean rendre (Pret pret,LocalDate date){
        LocalDateTime now = LocalDateTime.now();
        pret.setStatut("TERMINE");

        PretStatus pretStatus = new PretStatus();
        pretStatus.setStatu("TERMINE");
        pretStatus.setDateIn(now);
        pretStatus.setDateDebut(date);
        pretStatus.setDateFin(date);
        pretStatus.setPret(pret);
        
        pretRepository.save(pret);
        pretStatusRepository.save(pretStatus);

        if (pret.getDateRetourPrevue().isBefore(date)) {
            Adhesion last = utilisateurService.getLastAdhesionByUtilisateur(pret.getUtilisateur());
            Integer nbJour ;
            if (last!=null) {
                nbJour = penaliteTypeRepository.findNbJoursPenaliteByTypeAdhesion(last.getTypeAdhesion().getId()).orElse(0);
            } else {
                nbJour = 2;
            }


            Penalite penalite = new Penalite();
            penalite.setDateIn(now);
            penalite.setDateDebut(date);
            penalite.setDateFin(date.plusDays(nbJour));
            penalite.setUtilisateur(pret.getUtilisateur());
            penalite.setMotif("pret en retard date prevu "+pret.getDateRetourPrevue()+" date de rendu "+date+" . Penaliser jusqu'a "+date.plusDays(nbJour));

            penaliteRepository.save(penalite);
            
            return false;
        }


        return true;

    }
    public List<Pret> listPretEnCours(Utilisateur utilisateur){
         return pretRepository.findByStatutAndUtilisateur("EN_COURS", utilisateur);
    }
    public List<Pret> listPretProlonger(Utilisateur utilisateur){
        return pretRepository.findByStatutAndUtilisateur("PROLONGE", utilisateur);
    }
    public boolean isDayOff(LocalDate date){
        int nbJM = jfRepository.findByJourAndMois(date.getDayOfMonth(), date.getMonthValue()).size();
        int nbD = jfRepository.findByDateFix(date).size();
        DayOfWeek dow = date.getDayOfWeek();
        return (dow == DayOfWeek.SUNDAY || nbJM>0 || nbD>0);
         
    }


    public LocalDate ajusteDay(LocalDate date){
        while (isDayOff(date)) {
            date=date.plusDays(1);
        }
        return date;
    }
   


}
