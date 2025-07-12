package mg.itu.biblio.repositories;

import mg.itu.biblio.models.PretStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PretStatusRepository extends JpaRepository<PretStatus, Integer> {
    List<PretStatus> findByIdPretOrderByDateDebutDesc(Integer pretId);
    
    @Query("SELECT ps FROM PretStatus ps WHERE ps.pret.id = :pretId AND ps.dateFin IS NULL")
    Optional<PretStatus> findCurrentStatus(Integer pretId);
    
}