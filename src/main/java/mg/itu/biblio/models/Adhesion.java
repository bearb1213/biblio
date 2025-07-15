package mg.itu.biblio.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "adhesion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adhesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date_in", nullable = false)
    private LocalDate dateIn;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_type", nullable = false)
    private AdhesionType typeAdhesion;
}