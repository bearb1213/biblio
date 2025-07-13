package mg.itu.biblio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.itu.biblio.models.Utilisateur;
import mg.itu.biblio.models.UtilisateurType;
import mg.itu.biblio.repositories.UtilisateurRepository;
import mg.itu.biblio.repositories.UtilisateurTypeRepository;

@Service
public class UtilisateurService {
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private UtilisateurTypeRepository utilisateurTypeRepository;

    public Utilisateur getUtilisateurById(Integer id) {
        return utilisateurRepository.findById(id).orElse(null);
    }
    public Utilisateur getUtilisateurByEmail(String email,String mdp) {
        return utilisateurRepository.findByEmailAndMotDePasse(email,mdp).orElse(null);
    }

    public List<Utilisateur> getAllUtilisateurs() {
        UtilisateurType utilisateurType = utilisateurTypeRepository.findById(2).orElse(null);
        return utilisateurRepository.findByTypeUtilisateur(utilisateurType);
    }

}
