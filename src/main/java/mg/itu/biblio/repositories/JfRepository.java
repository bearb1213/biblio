package mg.itu.biblio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mg.itu.biblio.models.Jf;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JfRepository extends JpaRepository<Jf, Integer> {

    // Trouver par jour et mois
    List<Jf> findByJourAndMois(Integer jour, Integer mois);

    // Trouver par date exacte
    List<Jf> findByDateFix(LocalDate date);

    // Trouver les jours fériés entre deux dates
    List<Jf> findByDateFixBetween(LocalDate startDate, LocalDate endDate);

    // Trouver les jours fériés pour un mois spécifique
    List<Jf> findByMois(Integer mois);

    // Vérifier si une date spécifique est un jour férié
    boolean existsByDateFix(LocalDate date);
}