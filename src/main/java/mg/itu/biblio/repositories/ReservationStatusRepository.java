package mg.itu.biblio.repositories;

import mg.itu.biblio.models.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import mg.itu.biblio.models.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface ReservationStatusRepository extends JpaRepository<ReservationStatus, Integer> {

    // Méthodes de base fournies automatiquement
    
    // Méthodes personnalisées
    List<ReservationStatus> findByReservationId(Integer reservationId);
    List<ReservationStatus> findByStatus(String status);
    List<ReservationStatus> findByDateInBetween(LocalDateTime start, LocalDateTime end);
    
    // Trouver le dernier statut d'une réservation
    @Query("SELECT rs FROM ReservationStatus rs WHERE rs.reservation.id = :reservationId ORDER BY rs.dateIn DESC LIMIT 1")
    Optional<ReservationStatus> findLatestByReservationId(Integer reservationId);

    Optional<ReservationStatus> findFirstByReservationOrderByDateInDesc(Reservation reservation);

    List<Integer> findDistinctReservationIdByStatus(String status);


}