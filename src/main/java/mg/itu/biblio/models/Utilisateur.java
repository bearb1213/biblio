package mg.itu.biblio.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "utilisateur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nom", length = 255)
    private String nom;

    @Column(name = "prenom", length = 255)
    private String prenom;

    @Column(name = "email", length = 255, unique = true)
    private String email;

    @Column(name = "mdp", length = 255)
    private String motDePasse;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "date_in")
    private LocalDate dateInscription;

    @ManyToOne
    @JoinColumn(name = "id_type", referencedColumnName = "id", nullable = false)
    private UtilisateurType typeUtilisateur;

    public int age(){
        LocalDate now =LocalDate.now();
        Period age = Period.between(dateNaissance,now);
        return age.getYears();
    }

}