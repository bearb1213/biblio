package mg.itu.biblio.repositories;

import mg.itu.biblio.models.Prolongement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import mg.itu.biblio.models.Pret;
import java.util.List;

@Repository
public interface ProlongementRepository extends JpaRepository<Prolongement, Integer> {
    
    List<Prolongement> findByPret(Pret pret);
    
    List<Prolongement> findByStatut(String statut);
    
    
}