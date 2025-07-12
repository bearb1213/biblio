package mg.itu.biblio.repositories;

import mg.itu.biblio.models.Prolongement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProlongementRepository extends JpaRepository<Prolongement, Integer> {
    
    List<Prolongement> findByIdPret(Integer pretId);
    
    List<Prolongement> findByStatut(String statut);
    
    @Query("SELECT p FROM Prolongement p WHERE p.pret.id = :pretId AND p.statut = 'DEMANDE'")
    List<Prolongement> findDemandesEnAttente(Integer pretId);
}