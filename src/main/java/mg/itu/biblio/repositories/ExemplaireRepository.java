package mg.itu.biblio.repositories;

import mg.itu.biblio.models.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {
    // Méthodes de base fournies automatiquement :
    // save(), findById(), findAll(), deleteById(), etc.
    
    // Méthodes personnalisées utiles :
    List<Exemplaire> findByLivreId(Integer livreId);
    List<Exemplaire> findByDateInBetween(LocalDate startDate, LocalDate endDate);
}