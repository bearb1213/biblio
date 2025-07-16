package mg.itu.biblio.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "jf")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Jf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "jour" , nullable = true)
    private Integer jour;

    @Column(name = "mois" , nullable = true)
    private Integer mois;

    @Column(name = "date_fix" , nullable = true)
    private LocalDate dateFix;

}