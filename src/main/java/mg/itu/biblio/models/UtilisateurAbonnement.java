package mg.itu.biblio.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurAbonnement { 
    
    private Utilisateur utilisateur;

    private Adhesion adhesion ;

    private List<Penalite> penalites;

    private List<Pret> pret;

    private Integer quotaPret;

    private Integer quotaProlongement;

    private Integer quotaReservation;

    
}
