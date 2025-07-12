package mg.itu.biblio.repositories;

import mg.itu.biblio.models.AdhesionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdhesionTypeRepository extends JpaRepository<AdhesionType, Integer> {
    // Méthodes CRUD de base héritées automatiquement :
    // save(), findById(), findAll(), deleteById(), etc.
}