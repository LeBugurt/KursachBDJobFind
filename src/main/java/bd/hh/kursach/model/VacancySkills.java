package bd.hh.kursach.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "tb_vacancies_skills")
@AllArgsConstructor
@NoArgsConstructor
public class VacancySkills extends EntityID{

    @Column(name = "id_vacancies", nullable = false)
    private UUID vacancyId;

    @Column(name = "id_skills", nullable = false)
    private UUID skillId;
}
