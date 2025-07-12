package mg.itu.biblio.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "action", length = 50, nullable = false)
    private String action; // Ex: "EMPRUNT", "RENOUVELLEMENT", "RESERVATION"

    @Column(name = "nb", nullable = false)
    private Integer nombreMax;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type", nullable = false)
    private AdhesionType typeAdhesion;
}