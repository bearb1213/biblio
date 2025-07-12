package mg.itu.biblio.repositories;

import mg.itu.biblio.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    
    // Méthodes de base fournies automatiquement
    
    // Méthodes personnalisées
    List<Reservation> findByUtilisateurId(Integer utilisateurId);
    List<Reservation> findByExemplaireId(Integer exemplaireId);
    List<Reservation> findByDateReservationBetween(LocalDateTime start, LocalDateTime end);
    List<Reservation> findByMotifContainingIgnoreCase(String keyword);
    
    // Vérifie si un exemplaire est déjà réservé
    boolean existsByExemplaireId(Integer exemplaireId);
}