package mg.itu.biblio.repositories;

import mg.itu.biblio.models.Reservation;
import mg.itu.biblio.models.Utilisateur;

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
    List<Reservation> findByDateInBetween(LocalDateTime start, LocalDateTime end);
    List<Reservation> findByMotifContainingIgnoreCase(String keyword);
    
    // Vérifie si un exemplaire est déjà réservé
    boolean existsByExemplaireId(Integer exemplaireId);

    // Compte le nombre de réservations pour un utilisateur donné
    Integer countByUtilisateurAndStatut(Utilisateur utilisateur, String statut);

    List<Reservation> findByStatut(String statut);
    

}