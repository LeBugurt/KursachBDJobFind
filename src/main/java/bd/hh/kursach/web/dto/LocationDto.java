package bd.hh.kursach.web.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {

    private UUID id;
    private String country;
    private String city;
    private String region;
}
