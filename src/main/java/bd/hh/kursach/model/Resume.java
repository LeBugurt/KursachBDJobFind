package bd.hh.kursach.model;

import bd.hh.kursach.model.enums.GradeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.UUID;

@Entity
@Table(name = "tb_resume")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Resume extends EntityID {

    private String position;

    private String surname;

    private String name;

    @CreatedDate
    private String patronymic;

    @Enumerated(EnumType.STRING)
    private GradeEnum grade;

    private String workingHours;

    private Boolean moving;

    private Boolean willingnessTravel;

    private Long salary;

    private UUID userId;

    @Column(name = "id_resume_status")
    private UUID statusId;

    @Column(name = "id_resume_location")
    private UUID locationID;

}



