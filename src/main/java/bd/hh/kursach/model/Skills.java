package bd.hh.kursach.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_skills")
public class Skills extends EntityID {

    private String skillsName;
}
