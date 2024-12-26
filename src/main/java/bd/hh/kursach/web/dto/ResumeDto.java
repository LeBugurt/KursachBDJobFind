package bd.hh.kursach.web.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class ResumeDto {

    private UUID id;
    private String position;
    private String surname;
    private String name;
    private String patronymic;
    private String grade;
    private String workingHours;
    private Boolean moving;
    private Boolean willingnessTravel;
    private Long salary;
    private String status;
    private UUID locationId;
    private UUID statusId;
    private List<String> skills;
    private String city;
    private String region;
    private String country;
}
