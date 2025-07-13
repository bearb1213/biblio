package mg.itu.biblio.repositories;

import mg.itu.biblio.models.Utilisateur;
import mg.itu.biblio.models.UtilisateurType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    // Méthodes de base fournies automatiquement
    
    // Méthodes personnalisées utiles
    Utilisateur findByEmail(String email);
    List<Utilisateur> findByTypeUtilisateurId(Integer typeId);
    List<Utilisateur> findByNomContainingOrPrenomContaining(String nom, String prenom);
    Optional<Utilisateur> findByEmailAndMotDePasse(String email, String motDePasse);

    List<Utilisateur> findByTypeUtilisateur(UtilisateurType type);

}