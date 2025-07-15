package mg.itu.biblio.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExemplaireDisponible {
    private Integer exemplaireId;
    private String disponibilite;
}
