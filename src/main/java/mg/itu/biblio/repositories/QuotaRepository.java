package mg.itu.biblio.repositories;

import mg.itu.biblio.models.Quota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuotaRepository extends JpaRepository<Quota, Integer> {

    List<Quota> findByTypeAdhesionId(Integer typeAdhesionId);
    
    Optional<Quota> findByTypeAdhesionIdAndAction(Integer typeAdhesionId, String action);
    
    @Query("SELECT q.nombreMax FROM Quota q WHERE q.typeAdhesion.id = :typeId AND q.action = :action")
    Optional<Integer> findNombreMaxByTypeAndAction(Integer typeId, String action);
}