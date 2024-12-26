package bd.hh.kursach.model;

import bd.hh.kursach.model.enums.RoleEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "tb_user")
@Getter
@Setter
public class User extends EntityID {

    private String surname;

    private String name;

    private String patronymic;

    private String email;

    private String phone;

    private String password;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;
}
