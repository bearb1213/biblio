package mg.itu.biblio.repositories;

import mg.itu.biblio.models.Exemplaire;
import mg.itu.biblio.models.Pret;
import mg.itu.biblio.models.Utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {

    Integer countByUtilisateurAndStatut(Utilisateur utilisateur, String statut);
 
    List<Pret> findByStatut(String statut);
}