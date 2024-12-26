package bd.hh.kursach.service;

import bd.hh.kursach.web.dto.ResumeDto;

import java.util.List;
import java.util.UUID;

public interface ResumeService {

    ResumeDto findResumeById(UUID id);

    ResumeDto createResume(ResumeDto resume);

    ResumeDto updateResume(ResumeDto resume);

    boolean deleteResumeById(UUID id);

    List<ResumeDto> findAllResume();
}