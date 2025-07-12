package mg.itu.biblio.repositories;

import mg.itu.biblio.models.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Integer> {
    // Méthodes CRUD de base fournies automatiquement
    // Méthodes personnalisables :
    List<Livre> findByTitreContainingIgnoreCase(String titre);
    List<Livre> findByAuteurId(Integer auteurId);
}