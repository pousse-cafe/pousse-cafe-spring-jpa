package poussecafe.spring.jpa.storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringJpaConfig {

    @Bean
    public SpringJpaStorage springJpaStorage() {
        return SpringJpaStorage.instance();
    }
}
