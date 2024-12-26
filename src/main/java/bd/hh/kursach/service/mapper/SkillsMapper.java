package bd.hh.kursach.service.mapper;

import bd.hh.kursach.model.Skills;
import bd.hh.kursach.web.dto.SkillsDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface SkillsMapper {

    Skills toSkillsEntity(SkillsDto skillsDto);

    Set<Skills> toSkillsEntitySet(Set<SkillsDto> skillsDtos);

    SkillsDto toSkillsDto(Skills skills);
}
