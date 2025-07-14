package mg.itu.biblio.repositories;

import mg.itu.biblio.models.Penalite;
import mg.itu.biblio.models.Utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PenaliteRepository extends JpaRepository<Penalite, Integer> {

    List<Penalite> findByUtilisateurId(Integer utilisateurId);
    
    List<Penalite> findByDateFinAfter(LocalDateTime date);
    
    @Query("SELECT p FROM Penalite p WHERE p.utilisateur.id = :userId AND p.dateFin > CURRENT_TIMESTAMP")
    List<Penalite> findPenalitesActivesByUtilisateur(Integer userId);
    
    @Query("SELECT COUNT(p) > 0 FROM Penalite p WHERE p.utilisateur.id = :userId AND p.dateFin > CURRENT_TIMESTAMP")
    boolean utilisateurAvecPenaliteActive(Integer userId);

    @Query("SELECT COUNT(p) > 0 FROM Penalite p WHERE p.utilisateur = :utilisateur " +
       "AND :date BETWEEN p.dateDebut AND p.dateFin")
    boolean existsForUserInDateRange(
    @Param("utilisateur") Utilisateur utilisateur,
    @Param("date") LocalDate date);


    // Solution 2: Version avec @Query (plus explicite)
    @Query("SELECT p FROM Penalite p WHERE p.utilisateur = :utilisateur " +
           "AND (:date BETWEEN p.dateDebut AND p.dateFin)")
    List<Penalite> findPenalitesByUtilisateurAndDateRange(
        @Param("utilisateur") Utilisateur utilisateur,
        @Param("date") LocalDate date
    );

}