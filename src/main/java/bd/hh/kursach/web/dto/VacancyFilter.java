package bd.hh.kursach.web.dto;

import bd.hh.kursach.model.enums.GradeEnum;
import bd.hh.kursach.model.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class VacancyFilter {
    String country;
    String region;
    String city;
    StatusEnum status;
    GradeEnum grade;
    String position;
    List<String> skills;
    Long salaryFrom;
    Long salaryTo;
    Short experience;
    String currency;
}
