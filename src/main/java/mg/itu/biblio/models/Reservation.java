package mg.itu.biblio.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date_in", nullable = false, updatable = false)
    private LocalDateTime dateIn;

    @Column(name = "date_reservation", nullable = false, updatable = false)
    private LocalDate dateReservation;

    @Column(name = "motif", columnDefinition = "TEXT")
    private String motif;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_exemplaire", nullable = false)
    private Exemplaire exemplaire;

    @Column(name = "statut", length = 50, nullable = false)
    private String statut; // "EN_ATTENTE", "CONFIRMEE", "ANNULEE", etc.
}