package bd.hh.kursach.service.mapper;

import bd.hh.kursach.model.Resume;
import bd.hh.kursach.web.dto.ResumeDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {LocationsMapper.class, SkillsMapper.class}
)
public interface ResumeMapper {

    @Mapping(target = "grade", source = "resume.grade")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "statusId", source = "statusId")
    ResumeDto toResumeDto(Resume resume);

    @Mapping(target = "statusId", source = "statusId")
    Resume toResumeEntity(ResumeDto resumeDto);

    @Mapping(target = "statusId", source = "statusId")
    void resumeToEntity(@MappingTarget Resume  resume, ResumeDto resumeDto);

}
