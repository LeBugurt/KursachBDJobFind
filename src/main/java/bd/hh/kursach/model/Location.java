package bd.hh.kursach.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_location")
public class Location extends EntityID {

    private String country;

    private String city;

    private String region;
}
