package mg.itu.biblio.services;

import org.hibernate.id.IntegralDataTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mg.itu.biblio.models.Exemplaire;
import mg.itu.biblio.models.Quota;
import mg.itu.biblio.models.Reservation;
import mg.itu.biblio.models.ReservationStatus;
import mg.itu.biblio.models.Utilisateur;
import mg.itu.biblio.repositories.QuotaRepository;
import mg.itu.biblio.repositories.ReservationRepository;
import mg.itu.biblio.repositories.ReservationStatusRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationStatusRepository reservationStatusRepository;

    @Autowired
    private QuotaRepository quotaRepository;

    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
    public Reservation findById(Integer id) {
        return reservationRepository.findById(id).orElse(null);
    }
    public ReservationStatus saveStatus(ReservationStatus reservationStatus) {
        return reservationStatusRepository.save(reservationStatus);
    }
    public ReservationStatus lastStatus(Reservation reservation) {
        return reservationStatusRepository.findFirstByReservationOrderByDateInDesc(reservation).orElse(null);
    }

    
    public Integer getReservationQuota(Integer typeAdhesionId) {
        return quotaRepository.findNombreMaxByTypeAndAction(typeAdhesionId, "reservation").orElse(0);
    }

    public Integer getNbReservation(Utilisateur utilisateur) {
        return reservationRepository.countByUtilisateurAndStatut(utilisateur, "EN_ATTENTE");
    }

    @Transactional
    public boolean createReservation(LocalDate dateReservation , String motif , Utilisateur utilisateur , Exemplaire exemplaire ) throws Exception {
        try {
            LocalDateTime now= LocalDateTime.now();
            Reservation reservation = new Reservation();
            reservation.setDateIn(now);
            reservation.setDateReservation(dateReservation);
            reservation.setMotif(motif);
            reservation.setUtilisateur(utilisateur);
            reservation.setExemplaire(exemplaire);
            reservation.setStatut("EN_ATTENTE");

            ReservationStatus reservationStatus = new ReservationStatus();
            reservationStatus.setStatus("EN_ATTENTE");
            reservationStatus.setDateIn(now);
            reservationStatus.setReservation(reservation);

            reservationRepository.save(reservation);
            reservationStatusRepository.save(reservationStatus);

        } catch (Exception e) {
            throw e;   
        }
        return true;
    }

    public boolean accepteReservation(Reservation reservation) throws Exception {
        try {
            ReservationStatus reservationStatus = new ReservationStatus();
            reservationStatus.setStatus("CONFIRMEE");
            reservationStatus.setDateIn(LocalDateTime.now());
            reservationStatus.setReservation(reservation);
            reservationStatusRepository.save(reservationStatus);

            reservation.setStatut("CONFIRMEE");
            reservationRepository.save(reservation);

        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public boolean refuseReservation(Reservation reservation) throws Exception {
        try {
            ReservationStatus reservationStatus = new ReservationStatus();
            reservationStatus.setStatus("ANNULEE");
            reservationStatus.setDateIn(LocalDateTime.now());
            reservationStatus.setReservation(reservation);
            reservationStatusRepository.save(reservationStatus);

            reservation.setStatut("ANNULEE");
            reservationRepository.save(reservation);

        } catch (Exception e) {
            throw e;
        }
        return true;
    }
    public List<Reservation> findAllReservationByStatut(String statut)
    {
        return reservationRepository.findByStatut(statut);
    }


}
