package mg.itu.biblio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.itu.biblio.models.Adhesion;
import mg.itu.biblio.models.AdhesionType;
import mg.itu.biblio.models.Utilisateur;
import mg.itu.biblio.models.UtilisateurType;
import mg.itu.biblio.repositories.AdhesionRepository;
import mg.itu.biblio.repositories.AdhesionTypeRepository;
import mg.itu.biblio.repositories.UtilisateurRepository;
import mg.itu.biblio.repositories.UtilisateurTypeRepository;

@Service
public class UtilisateurService {
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private UtilisateurTypeRepository utilisateurTypeRepository;

    @Autowired
    private AdhesionTypeRepository adhesionTypeRepository;

    @Autowired
    private AdhesionRepository adhesionRepository;

    public Utilisateur getUtilisateurById(Integer id) {
        return utilisateurRepository.findById(id).orElse(null);
    }
    public Utilisateur getUtilisateurByEmailAndMotDePasse(String email,String mdp) {
        return utilisateurRepository.findByEmailAndMotDePasse(email,mdp).orElse(null);
    }

    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email).orElse(null);
    }

    public List<Utilisateur> getAllUtilisateurs() {
        UtilisateurType utilisateurType = utilisateurTypeRepository.findById(2).orElse(null);
        return utilisateurRepository.findByTypeUtilisateur(utilisateurType);
    }
    public UtilisateurType getTypeUtilisateur(){
        return utilisateurTypeRepository.findById(2).orElse(null);
    }
    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public List<AdhesionType> getAllAdhesionTypes() {
        return adhesionTypeRepository.findAll();
    }

    public AdhesionType getAdhesionTypeById(Integer id) {
        return adhesionTypeRepository.findById(id).orElse(null);
    }

    public Adhesion saveAdhesion(Adhesion adhesion){
        return adhesionRepository.save(adhesion);
    }

    public Adhesion getLastAdhesionByUtilisateur(Utilisateur utilisateur) {
        return adhesionRepository.findFirstByUtilisateurOrderByDateInDesc(utilisateur).orElse(null);
    }   



}
