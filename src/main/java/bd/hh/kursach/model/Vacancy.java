package bd.hh.kursach.model;

import bd.hh.kursach.model.enums.GradeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_vacancy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy extends EntityID {

    private String position;

    private String company;

    private Long salary;

    private String currency;

    private String description;

    @Enumerated(EnumType.STRING)
    private GradeEnum grade;

    private LocalDate publicationDate;

    private String email;

    private UUID userId;

    @Column(name = "id_vacancies_status")
    private UUID statusId;

    @Column(name = "id_vacancies_location")
    private UUID idLocation;
}
