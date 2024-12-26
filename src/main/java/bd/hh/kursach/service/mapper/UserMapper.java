package bd.hh.kursach.service.mapper;


import bd.hh.kursach.model.User;
import bd.hh.kursach.web.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDto toUserDto(User user);

    List<UserDto> toUserDtolist(List<User> hrs);

    User toUserEntity(UserDto hrDto);
}
