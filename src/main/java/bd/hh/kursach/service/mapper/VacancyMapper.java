package bd.hh.kursach.service.mapper;

import bd.hh.kursach.model.Location;
import bd.hh.kursach.model.Skills;
import bd.hh.kursach.model.Vacancy;
import bd.hh.kursach.model.enums.GradeEnum;
import bd.hh.kursach.web.dto.LocationDto;
import bd.hh.kursach.web.dto.SkillsDto;
import bd.hh.kursach.web.dto.VacancyDto;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class VacancyMapper {

    public VacancyDto vacancyToVacancyDto(Vacancy vacancy) {

        VacancyDto vacancyDto = new VacancyDto();

        vacancyDto.setId(vacancy.getId());
        vacancyDto.setPosition(vacancy.getPosition());
        vacancyDto.setCompany(vacancy.getCompany());
        vacancyDto.setSalary(vacancy.getSalary());
        vacancyDto.setCurrency(vacancy.getCurrency());
        vacancyDto.setDescription(vacancy.getDescription());
        vacancyDto.setEmail(vacancy.getEmail());
        vacancyDto.setGrade(vacancy.getGrade().toString());
        vacancyDto.setUserId(vacancy.getUserId());

        return vacancyDto;
    }

    public Vacancy vacancyDtoToVacancy(VacancyDto vacancyDto) {
        Vacancy vacancy = new Vacancy();

        vacancy.setId(vacancyDto.getId());
        vacancy.setPosition(vacancyDto.getPosition());
        vacancy.setCompany(vacancyDto.getCompany());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setCurrency(vacancyDto.getCurrency());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setEmail(vacancyDto.getEmail());
        vacancy.setUserId(vacancyDto.getUserId());
        vacancy.setStatusId(vacancyDto.getStatusId());

        if (vacancyDto.getGrade() != null) {
            vacancy.setGrade(GradeEnum.valueOf(vacancyDto.getGrade()));
        }

        return vacancy;
    }

    public Location vacancyDtoToLocation(VacancyDto vacancyDto) {
        Location location = new Location();
        location.setCity(vacancyDto.getCity());
        location.setCountry(vacancyDto.getCountry());
        location.setRegion(vacancyDto.getRegion());

        return location;
    }

    public void locationToVacancyDto(Location location, VacancyDto vacancyDto) {

        vacancyDto.setCountry(location.getCountry());
        vacancyDto.setCity(location.getCity());
        vacancyDto.setRegion(location.getRegion());
    }
    public LocationDto locationToLocationDto(Location location) {
        LocationDto locationDto = new LocationDto();

        locationDto.setCountry(location.getCountry());
        locationDto.setCity(location.getCity());
        locationDto.setRegion(location.getRegion());

        return locationDto;
    }

    public SkillsDto skillsToSkillsDto(Skills skills) {
        SkillsDto skillDto = new SkillsDto();

        skillDto.setId(skills.getId());
        skillDto.setSkillName(skills.getSkillsName());

        return skillDto;
    }

    public Set<SkillsDto> skillsToSkillsDto(Set<Skills> skills) {
        Set<SkillsDto> skillDtos = new HashSet<>();
        if (skills != null) {
            for (Skills skill : skills) {
                skillDtos.add(skillsToSkillsDto(skill));
            }
        }
        return skillDtos;
    }

    public void updateVacancyFromDto(VacancyDto vacancyDto, Vacancy vacancy) {
        if (vacancyDto.getPosition() != null) {
            vacancy.setPosition(vacancyDto.getPosition());
        }
        if (vacancyDto.getCompany() != null) {
            vacancy.setCompany(vacancyDto.getCompany());
        }
        if (vacancyDto.getSalary() != null) {
            vacancy.setSalary(vacancyDto.getSalary());
        }
        if (vacancyDto.getCurrency() != null) {
            vacancy.setCurrency(vacancyDto.getCurrency());
        }
        if (vacancyDto.getDescription() != null) {
            vacancy.setDescription(vacancyDto.getDescription());
        }
        if (vacancyDto.getEmail() != null) {
            vacancy.setEmail(vacancyDto.getEmail());
        }
        if (vacancyDto.getGrade() != null) {
            vacancy.setGrade(GradeEnum.valueOf(vacancyDto.getGrade()));
        }
    }
}
