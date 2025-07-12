package mg.itu.biblio.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "livre")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "titre", length = 255, nullable = false)
    private String titre;

    @Column(name = "synopsis", columnDefinition = "TEXT")
    private String synopsis;

    @Column(name = "date_premier_edition")
    private LocalDate datePremierEdition;

    @Column(name = "langue", length = 50)
    private String langue;

    @Column(name = "nb_page", nullable = false)
    private Integer nbPage;

    @Column(name = "restriction")
    private Integer restriction;

    @ManyToOne
    @JoinColumn(name = "id_auteur", referencedColumnName = "id", nullable = false)
    private Auteur auteur;

    @ManyToMany
    @JoinTable(
        name = "livre_genre",
        joinColumns = @JoinColumn(name = "id_livre"),
        inverseJoinColumns = @JoinColumn(name = "id_genre")
    )
    private List<Genre> genres; 
}