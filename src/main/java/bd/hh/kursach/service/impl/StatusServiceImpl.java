package bd.hh.kursach.service.impl;

import bd.hh.kursach.exception.EqualStatusException;
import bd.hh.kursach.exception.ResourceNotFoundException;
import bd.hh.kursach.model.Resume;
import bd.hh.kursach.model.Vacancy;
import bd.hh.kursach.model.enums.StatusEnum;
import bd.hh.kursach.repository.ResumeRepository;
import bd.hh.kursach.repository.StatusRepository;
import bd.hh.kursach.repository.VacancyRepository;
import bd.hh.kursach.service.StatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;
    VacancyRepository vacancyRepository;
    ResumeRepository resumeRepository;

    @Override
    public ResponseEntity<?> updateVacancyStatus(String statusName, UUID idVacancy) {
        Vacancy vacancy = (vacancyRepository.findById(idVacancy)
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found")));

        if (statusRepository.findById(vacancy.getStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("Status not found"))
                .getStatus().toString().equals(statusName)) {throw new EqualStatusException("The status is already relevant");}

        UUID idNewStatus = statusRepository.findByStatus(StatusEnum.valueOf(statusName));

        vacancy.setStatusId(idNewStatus);
        vacancyRepository.save(vacancy);

        return ResponseEntity.ok("Vacancy status updated successfully");
    }

    @Override
    public ResponseEntity<?> updateResumeStatus(UUID idResume, String statusName) {
       Resume resume = (resumeRepository.findById(idResume)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found")));

        if (statusRepository.findById(resume.getStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("Status not found"))
                .getStatus().toString().equals(statusName)) {throw new EqualStatusException("The status is already relevant");}

        UUID idNewStatus = statusRepository.findByStatus(StatusEnum.valueOf(statusName));

        resume.setStatusId(idNewStatus);
        resumeRepository.save(resume);

        return ResponseEntity.ok("Vacancy status updated successfully");
    }
}

