package mg.itu.biblio.repositories;

import mg.itu.biblio.models.PenaliteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PenaliteTypeRepository extends JpaRepository<PenaliteType, Integer> {

    Optional<PenaliteType> findByTypeAdhesionId(Integer typeAdhesionId);
    
    @Query("SELECT p.nbJoursPenalite FROM PenaliteType p WHERE p.typeAdhesion.id = :typeAdhesionId")
    Optional<Integer> findNbJoursPenaliteByTypeAdhesion(Integer typeAdhesionId);
}