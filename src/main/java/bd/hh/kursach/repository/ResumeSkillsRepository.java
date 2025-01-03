package bd.hh.kursach.repository;

import bd.hh.kursach.model.ResumeSkills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ResumeSkillsRepository extends JpaRepository<ResumeSkills, UUID> {
    @Query("""
        SELECT DISTINCT s.skillsName
        FROM ResumeSkills rs
        JOIN Skills s ON rs.skillId = s.id
        WHERE rs.resumeId = :idResume
    """)
    List<String> findAllById(@Param("idResume")UUID idResume);
}
