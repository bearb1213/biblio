package mg.itu.biblio.repositories;

import mg.itu.biblio.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    // Méthodes CRUD automatiques
    boolean existsByGenre(String genre);  // Vérifie si un genre existe déjà
    Genre findByGenre(String genre);     // Trouve un genre par son nom
}