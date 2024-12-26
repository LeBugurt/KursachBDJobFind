package bd.hh.kursach.service.impl;

import bd.hh.kursach.exception.IdNotExistException;
import bd.hh.kursach.exception.ResourceNotFoundException;
import bd.hh.kursach.model.Location;
import bd.hh.kursach.model.Resume;
import bd.hh.kursach.repository.LocationRepository;
import bd.hh.kursach.repository.ResumeRepository;
import bd.hh.kursach.repository.ResumeSkillsRepository;
import bd.hh.kursach.repository.StatusRepository;
import bd.hh.kursach.service.ResumeService;
import bd.hh.kursach.service.mapper.ResumeMapper;
import bd.hh.kursach.web.dto.ResumeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final ResumeMapper resumeMapper;
    private final ResumeSkillsRepository resumeSkillsRepository;
    private final StatusRepository statusRepository;
    private final LocationRepository locationRepository;

    @Transactional
    public ResumeDto createResume(ResumeDto resumeDto) {
        Resume resume = resumeMapper.toResumeEntity(resumeDto);
        Resume savedResume = resumeRepository.save(resume);
        return resumeMapper.toResumeDto(savedResume);
    }

    public ResumeDto findResumeById(UUID id) {
        return resumeRepository.findById(id)
                .map(resumeMapper::toResumeDto)
                .orElseThrow(() -> new IdNotExistException(id));
    }

    @Transactional
    public ResumeDto updateResume(ResumeDto resumeDto) {
        Resume resume = resumeRepository.findById(resumeDto.getId()).orElseThrow(() -> new IdNotExistException(resumeDto.getId()));
        resumeMapper.resumeToEntity(resume ,  resumeDto);
        return resumeMapper.toResumeDto(resumeRepository.save(resume));
    }

    public boolean deleteResumeById(UUID id) {
        if (resumeRepository.existsById(id)) {
            resumeRepository.deleteById(id);
            return true;
        } else {
            throw new IdNotExistException(id);
        }
    }

    @Override
    public List<ResumeDto> findAllResume() {
        List<Resume> resumes = resumeRepository.findAll();
        List<ResumeDto> resumeDtos = new ArrayList<>();
        for (Resume resume : resumes) {
            ResumeDto resumeDto = resumeMapper.toResumeDto(resume);
            resumeDto.setSkills(resumeSkillsRepository.findAllById(resume.getId()));
            resumeDto.setStatus(statusRepository.findById(resume.getStatusId())
                    .orElseThrow(() -> new ResourceNotFoundException("Status not found with id: " + resume.getStatusId())).getStatus().toString());
            Location location = (locationRepository.findById(resume.getLocationID()))
                    .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + resume.getLocationID()));
            resumeDto.setCity(location.getCity());
            resumeDto.setCountry(location.getCountry());
            resumeDto.setRegion(location.getRegion());

            resumeDtos.add(resumeDto);
        }
        return resumeDtos;
    }
}
