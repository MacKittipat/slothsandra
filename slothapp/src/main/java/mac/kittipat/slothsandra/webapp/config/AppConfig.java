package mac.kittipat.slothsandra.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.TreeMap;

@Configuration
public class AppConfig {

    @Bean
    public Map<String, String> channelIdMap() {
        return new TreeMap<>();
    }

    @Bean
    public Map<String, String> userIdMap() {
        return new TreeMap<>();
    }
}
