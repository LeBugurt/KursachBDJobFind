package bd.hh.kursach.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class VacancyDto {

    private UUID id;

    private UUID userId;

    private String position;

    private String company;

    private Long salary;

    private String currency;

    private String description;

    private String country;

    private String city;

    private String region;

    private String grade;

    List<String> skills;

    UUID statusId;

    private String status;

    private String email;
}