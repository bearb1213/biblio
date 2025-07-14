package mg.itu.biblio.repositories;

import mg.itu.biblio.models.PretNbJour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface PretNbJourRepository extends JpaRepository<PretNbJour, Integer> {
    // Méthodes personnalisées si nécessaire
    // Exemple: Trouver par nombre de jours
    List<PretNbJour> findByNbJour(Integer nbJour);
    
    // Exemple: Trouver par type d'adhésion
    Optional<PretNbJour> findByAdhesionTypeId(Integer idType);

}