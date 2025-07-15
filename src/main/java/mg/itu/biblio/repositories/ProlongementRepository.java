package mg.itu.biblio.repositories;

import mg.itu.biblio.models.Prolongement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import mg.itu.biblio.models.Pret;
import java.util.List;

@Repository
public interface ProlongementRepository extends JpaRepository<Prolongement, Integer> {
    
    List<Prolongement> findByPret(Pret pret);
    
    List<Prolongement> findByStatut(String statut);
    
    @Query("SELECT p FROM Prolongement p " +
       "WHERE p.id IN (" +
       "   SELECT MAX(p2.id) FROM Prolongement p2 " +
       "   GROUP BY p2.pret.id" +
       ") AND p.statut = :statut " +  // Changed second WHERE to AND
       "ORDER BY p.dateDemande DESC")
    List<Prolongement> findLastProlongementByPret(@Param("statut") String statut);
}