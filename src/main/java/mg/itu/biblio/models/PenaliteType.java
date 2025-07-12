package mg.itu.biblio.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "penalite_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PenaliteType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nb_jour", nullable = false)
    private Integer nbJoursPenalite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type", nullable = false)
    private AdhesionType typeAdhesion;
}