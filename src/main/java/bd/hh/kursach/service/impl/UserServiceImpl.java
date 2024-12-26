package bd.hh.kursach.service.impl;

import bd.hh.kursach.exception.IdNotExistException;
import bd.hh.kursach.model.User;
import bd.hh.kursach.repository.UserRepository;
import bd.hh.kursach.service.UserService;
import bd.hh.kursach.service.mapper.UserMapper;
import bd.hh.kursach.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UUID createUser(UserDto userDto) {
        User user = userMapper.toUserEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user).getId();
    }

    @Override
    public UserDto findUserById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDto)
                .orElseThrow(() -> new IdNotExistException(id));
    }

    @Override
    public UUID updateUser(UserDto userDto) {
        if (!userRepository.existsById(userDto.getId())) {
            throw new IdNotExistException(userDto.getId());
        }
        return userRepository.save(userMapper.toUserEntity(userDto)).getId();
    }

    @Override
    public boolean deleteUserById(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            throw new IdNotExistException(id);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}
