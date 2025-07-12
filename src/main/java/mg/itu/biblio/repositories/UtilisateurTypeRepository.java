package mg.itu.biblio.repositories;

import mg.itu.biblio.models.UtilisateurType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurTypeRepository extends JpaRepository<UtilisateurType, Integer> {
    // Méthodes CRUD automatiques:
    // - save(), findById(), findAll(), deleteById(), etc.
}