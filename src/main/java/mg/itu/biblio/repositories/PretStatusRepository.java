package mg.itu.biblio.repositories;

import mg.itu.biblio.models.Pret;
import mg.itu.biblio.models.PretStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PretStatusRepository extends JpaRepository<PretStatus, Integer> {
    List<PretStatus> findByPretOrderByDateDebutDesc(Pret pret); 
    
    
}