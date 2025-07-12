package mg.itu.biblio.repositories;

import mg.itu.biblio.models.Auteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuteurRepository extends JpaRepository<Auteur, Integer> {
    // Pas besoin d'implémenter les méthodes CRUD de base :
    // save(), findById(), findAll(), deleteById() sont fournies automatiquement
}