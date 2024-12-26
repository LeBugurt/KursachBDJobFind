package bd.hh.kursach.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "yandex.disk")
public class DiskProp {
    private String oauthToken;
    private String uploadPath;
}
