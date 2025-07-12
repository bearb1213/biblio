package mg.itu.biblio.repositories;

import mg.itu.biblio.models.Pret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {

    // Méthodes de base fournies automatiquement
    
    // Méthodes personnalisées
    List<Pret> findByEmprunteurId(Integer utilisateurId);
    List<Pret> findByExemplaireId(Integer exemplaireId);
    
    @Query("SELECT p FROM Pret p WHERE p.dateRetourPrevue < CURRENT_TIMESTAMP AND p NOT IN (SELECT r.pret FROM Retour r)")
    List<Pret> findPretsEnRetard();
    
    List<Pret> findByDateEmpruntBetween(LocalDateTime debut, LocalDateTime fin);
    
    boolean existsByExemplaireIdAndDateRetourPrevueIsNull(Integer exemplaireId);
}