package mg.itu.biblio.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "statu", length = 50, nullable = false)
    private String status; // "EN_ATTENTE", "CONFIRMEE", "ANNULEE", etc.

    @CreationTimestamp
    @Column(name = "date_in", nullable = false, updatable = false)
    private LocalDateTime dateStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reservation", nullable = false)
    private Reservation reservation;
}