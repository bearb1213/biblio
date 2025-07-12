package mg.itu.biblio.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "pret_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PretStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "statu", nullable = false)
    private String statu; // "EN_COURS", "PROLONGE", "TERMINE"

    @Column(name = "date_in", updatable = false)
    private LocalDateTime dateDebut;

    @Column(name = "date_fin")
    private LocalDateTime dateFin;

    @ManyToOne
    @JoinColumn(name = "id_pret", nullable = false)
    private Pret pret;
}