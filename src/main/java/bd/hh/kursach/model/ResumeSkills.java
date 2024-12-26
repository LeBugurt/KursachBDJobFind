package bd.hh.kursach.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "tb_resume_skills")
public class ResumeSkills extends EntityID {

    @Column(name = "id_resume", nullable = false)
    private UUID resumeId;

    @Column(name = "id_skills", nullable = false)
    private UUID skillId;
}
