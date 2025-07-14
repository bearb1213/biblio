package mg.itu.biblio.repositories;

import mg.itu.biblio.models.Pret;
import mg.itu.biblio.models.PretStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PretStatusRepository extends JpaRepository<PretStatus, Integer> {
    List<PretStatus> findByPretOrderByDateInDesc(Pret pret); 

    @Query("SELECT CASE WHEN COUNT(ps) > 0 THEN true ELSE false END " +
        "FROM PretStatus ps " +
        "JOIN ps.pret p " +
        "JOIN p.exemplaire e " +
        "WHERE e.id = :exemplaireId " +
        "AND (:date BETWEEN ps.dateDebut AND ps.dateFin)")
    boolean existsByExemplaireAndDateBetween(
            @Param("exemplaireId") Integer exemplaireId,
            @Param("date") LocalDate date);

}