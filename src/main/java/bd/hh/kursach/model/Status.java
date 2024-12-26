package bd.hh.kursach.model;

import bd.hh.kursach.model.enums.StatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_status")
@AllArgsConstructor
@NoArgsConstructor
public class Status extends EntityID{

    @Enumerated(EnumType.STRING)
    private StatusEnum status;
}