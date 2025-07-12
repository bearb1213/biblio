package mg.itu.biblio.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "prolongement")
@Data
@NoArgsConstructor
public class Prolongement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "statu", length = 50, nullable = false)
    private String statut; // "DEMANDE", "ACCEPTE", "REFUSE"

    @CreationTimestamp
    @Column(name = "date_in", updatable = false)
    private LocalDateTime dateDemande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pret", nullable = false)
    private Pret pret;

}