package bd.hh.kursach.repository;

import bd.hh.kursach.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, UUID>, JpaSpecificationExecutor<Vacancy> {

    List<Vacancy> findAllByOrderByPublicationDateDesc();

    List<Vacancy> findAllByOrderByPublicationDateAsc();

    List<Vacancy> findAllByUserId(UUID userId);

    @Query("""
    SELECT v FROM Vacancy v
    LEFT JOIN Status vs ON v.statusId = vs.id
    LEFT JOIN Location l ON v.idLocation = l.id
    WHERE LOWER(v.position) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
          LOWER(v.company) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
          LOWER(v.currency) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
          LOWER(v.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
          LOWER(v.grade) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
          LOWER(vs.status) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
          LOWER(l.country) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
          LOWER(l.region) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
          LOWER(l.city) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Vacancy> findAllByKeyword(@Param("keyword") String keyword);
}
