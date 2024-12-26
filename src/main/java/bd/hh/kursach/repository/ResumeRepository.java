package bd.hh.kursach.repository;

import bd.hh.kursach.model.Resume;
import bd.hh.kursach.web.dto.ResumeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, UUID>, JpaSpecificationExecutor<Resume> {

    @Query("""
    SELECT r FROM Resume r
    LEFT JOIN Status rs ON r.statusId = rs.id
    LEFT JOIN Location l ON r.locationID = l.id
    LEFT JOIN ResumeSkills sr ON r.id = sr.resumeId
    LEFT JOIN Skills s ON sr.skillId = s.id
    """)
    List<ResumeDto> findAllResume();
}

