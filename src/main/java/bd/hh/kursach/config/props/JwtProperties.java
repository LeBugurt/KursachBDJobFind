package bd.hh.kursach.config.props;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.Key;

@Setter
@Component
@ConfigurationProperties(prefix = "my-jwt")
public class JwtProperties {
    private String key;

    public Key getKey() {
        return getSignInKey();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
