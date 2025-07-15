package mg.itu.biblio.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pret")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_in", updatable = false)
    private LocalDateTime dateIn;

    @Column(name = "date_retour_prevue")
    private LocalDate dateRetourPrevue;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_exemplaire", nullable = false)
    private Exemplaire exemplaire;

    @Column(name = "statut" , nullable =  false , length = 50)
    private String statut; // "EN_COURS", "PROLONGE", "TERMINE"

    @Column(name = "type" , length = 50 , nullable = false)
    private String type;

    // Méthode utilitaire pour ajouter un statut
   
}