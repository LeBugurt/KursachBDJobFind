package bd.hh.kursach.service.impl;

import bd.hh.kursach.exception.IdNotExistException;
import bd.hh.kursach.exception.ResourceNotFoundException;
import bd.hh.kursach.model.*;
import bd.hh.kursach.model.enums.StatusEnum;
import bd.hh.kursach.repository.*;
import bd.hh.kursach.service.VacancyService;
import bd.hh.kursach.service.mapper.VacancyMapper;
import bd.hh.kursach.web.dto.VacancyDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private VacancyRepository vacancyRepository;
    private VacancySkillRepository vacancySkillRepository;
    private VacancyMapper vacancyMapper;
    private SkillRepository skillRepository;
    private LocationRepository locationRepository;
    private StatusRepository statusVacancyRepository;

    private List<VacancyDto> vacancyDtoList(List<Vacancy> vacancies) {
        List<VacancyDto> vacancyDtos = new ArrayList<>();
        for (Vacancy vacancy : vacancies) {
            VacancyDto vacancyDto = vacancyMapper.vacancyToVacancyDto(vacancy);
            vacancyDto.setSkills(vacancySkillRepository.findAllById(vacancy.getId()));
            vacancyDto.setStatus(statusVacancyRepository.findById(vacancy.getStatusId())
                    .orElseThrow(() -> new ResourceNotFoundException("Status not found with id: " + vacancy.getStatusId())).getStatus().toString());
            Location location = (locationRepository.findById(vacancy.getIdLocation()))
                    .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + vacancy.getIdLocation()));
            vacancyMapper.locationToVacancyDto(location, vacancyDto);

            vacancyDtos.add(vacancyDto);
        }

        return vacancyDtos;
    }

    @Override
    public List<VacancyDto> getAllVacancies() {
        List<Vacancy> vacancies = vacancyRepository.findAllByOrderByPublicationDateDesc();
        return vacancyDtoList(vacancies);
    }

    @Override
    public List<VacancyDto> getAllVacanciesOldFirst() {
        List<Vacancy> vacancies = vacancyRepository.findAllByOrderByPublicationDateAsc();
        return vacancyDtoList(vacancies);
    }

    @Override
    public List<VacancyDto> getVacanciesByKeyWord(String keyword) {
        List<Vacancy> vacancies = vacancyRepository.findAllByKeyword(keyword);
        return vacancyDtoList(vacancies);
    }

    @Override
    public List<VacancyDto> getVacanciesByTabOnlyMy(UUID userId) {
        List<Vacancy> vacancies = vacancyRepository.findAllByUserId(userId);
        return vacancyDtoList(vacancies);
    }

    public UUID createVacancy(VacancyDto vacancyDto) {
        UUID statusID = statusVacancyRepository.findByStatus(StatusEnum.active);
        Vacancy vacancy = vacancyMapper.vacancyDtoToVacancy(vacancyDto);
        if (statusID != null) {
            vacancy.setStatusId(statusID);
        } else {
            vacancy.setStatusId(statusVacancyRepository.save(new Status(StatusEnum.active)).getId());
        }
        vacancy.setPublicationDate(LocalDate.now());
        vacancy.setIdLocation(locationRepository.save(vacancyMapper.vacancyDtoToLocation(vacancyDto)).getId());
        Vacancy savedVacancy = vacancyRepository.save(vacancy);
        for (String skills : vacancyDto.getSkills()) {
            Skills skill = new Skills();
            skill.setSkillsName(skills);
            Skills savedSkill = skillRepository.save(skill);
            vacancySkillRepository.save(new VacancySkills(savedVacancy.getId(), savedSkill.getId()));
        }
        return savedVacancy.getId();
    }

    public VacancyDto findVacancyById(UUID id) {
        return vacancyRepository.findById(id)
                .map(vacancyMapper::vacancyToVacancyDto)
                .orElseThrow(() -> new IdNotExistException(id));
    }

    public VacancyDto updateVacancy(VacancyDto vacancyDto) {
        Vacancy vacancy = vacancyRepository.findById(vacancyDto.getId()).orElseThrow(() -> new IdNotExistException(vacancyDto.getId()));
        vacancyMapper.updateVacancyFromDto(vacancyDto, vacancy);
        return vacancyMapper.vacancyToVacancyDto(vacancyRepository.save(vacancy));
    }

    public boolean deleteVacancyById(UUID id) {
        if (vacancyRepository.existsById(id)) {
            vacancyRepository.deleteById(id);
            return true;
        } else {
            throw new IdNotExistException(id);
        }
    }
}
