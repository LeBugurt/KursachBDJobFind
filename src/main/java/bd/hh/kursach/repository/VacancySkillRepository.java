package bd.hh.kursach.repository;

import bd.hh.kursach.model.VacancySkills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VacancySkillRepository extends JpaRepository<VacancySkills, UUID> {
    @Query("""
        SELECT DISTINCT s.skillsName
        FROM VacancySkills vs
        JOIN Skills s ON vs.skillId = s.id
        WHERE vs.vacancyId = :idVacancy
    """)
    List<String> findAllById(@Param("idVacancy")UUID idVacancy);
}
