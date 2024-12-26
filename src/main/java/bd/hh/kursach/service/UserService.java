package bd.hh.kursach.service;

import bd.hh.kursach.web.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserService {

    UserDto findUserById(UUID id);

    UUID createUser(UserDto user);

    UUID updateUser(UserDto user);

    boolean deleteUserById(UUID id);

    UserDetails loadUserByUsername(String username);
}
