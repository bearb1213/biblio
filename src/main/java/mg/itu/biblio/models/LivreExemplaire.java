package mg.itu.biblio.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivreExemplaire {

    private Livre livre;

    private List<ExemplaireDisponible> exemplaires;

}
