package mg.itu.biblio.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "auteur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément (SERIAL)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nom", length = 255, nullable = false)
    private String nom;
}