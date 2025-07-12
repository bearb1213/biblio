package mg.itu.biblio.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "utilisateur_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    @Column(name = "id")
    private Integer id;  

    @Column(name = "type", length = 50, nullable = false)
    private String type;
}