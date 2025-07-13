package mg.itu.biblio.repositories;

import mg.itu.biblio.models.Adhesion;
import mg.itu.biblio.models.Utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdhesionRepository extends JpaRepository<Adhesion, Integer> {
    // Méthodes de base fournies automatiquement
    
    // Méthodes personnalisées utiles
    List<Adhesion> findByUtilisateurId(Integer utilisateurId);
    List<Adhesion> findByDateInBetween(LocalDate startDate, LocalDate endDate);
    List<Adhesion> findByTypeAdhesionId(Integer typeAdhesionId);
    List<Adhesion> findByDateFinBeforeAndUtilisateurId(LocalDate date, Integer utilisateurId);

    Optional<Adhesion> findFirstByUtilisateurOrderByDateInDesc(Utilisateur utilisateur);
    
}