package bd.hh.kursach.service.mapper;

import bd.hh.kursach.model.Location;
import bd.hh.kursach.web.dto.LocationDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface LocationsMapper {
    Location toLocationsEntity(LocationDto locationDto);

    Set<Location> toLocationsEntitySet(Set<LocationDto> locationsDto);

    LocationDto toLocationsDto(Location location);
}
