package bd.hh.kursach.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SkillsDto {

    private UUID id;
    private String skillName;

    public SkillsDto() {
        this.id = UUID.randomUUID();
        this.skillName = "";
    }
}
