package mg.itu.biblio.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private LocalDateTime dateEmprunt;

    @Column(name = "date_retour_prevue")
    private LocalDateTime dateRetourPrevue;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_exemplaire", nullable = false)
    private Exemplaire exemplaire;

    @OneToMany(mappedBy = "pret", cascade = CascadeType.ALL)
    private List<PretStatus> statuts = new ArrayList<>();

    // Méthode utilitaire pour ajouter un statut
   
}