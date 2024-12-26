package bd.hh.kursach.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private UUID id;

    private String surname;

    private String name;

    private String patronymic;

    private String email;

    private String phone;

    private String password;

    private String role;
}
