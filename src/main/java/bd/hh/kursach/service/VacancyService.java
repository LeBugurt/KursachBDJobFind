package bd.hh.kursach.service;

import bd.hh.kursach.web.dto.VacancyDto;

import java.util.List;
import java.util.UUID;

public interface VacancyService {

    List<VacancyDto> getAllVacancies();

    List<VacancyDto> getAllVacanciesOldFirst();

    List<VacancyDto> getVacanciesByKeyWord(String keyword);

    List<VacancyDto> getVacanciesByTabOnlyMy(UUID userId);

    VacancyDto findVacancyById(UUID id);

    UUID createVacancy(VacancyDto vacancy);

    VacancyDto updateVacancy(VacancyDto vacancy);

    boolean deleteVacancyById(UUID id);
}