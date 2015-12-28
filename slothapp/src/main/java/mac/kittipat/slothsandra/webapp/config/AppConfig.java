package mac.kittipat.slothsandra.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Map;
import java.util.TreeMap;

@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

    @Bean
    public Map<String, String> channelIdMap() {
        return new TreeMap<>();
    }

    @Bean
    public Map<String, String> userIdMap() {
        return new TreeMap<>();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
        super.addResourceHandlers(registry);
    }
}
