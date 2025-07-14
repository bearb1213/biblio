package mg.itu.biblio.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "pret_nbjour")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PretNbJour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nb_jour", nullable = false)
    private Integer nbJour;

    @ManyToOne
    @JoinColumn(name = "id_type", nullable = false)
    private AdhesionType adhesionType; // Relation ManyToOne vers AdhesionType

    // Méthodes utilitaires si nécessaire
}