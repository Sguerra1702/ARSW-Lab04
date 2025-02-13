package edu.eci.arsw.blueprints.config;

import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "edu.eci.arsw.blueprints")
public class AppConfig {


    @Bean
    public BlueprintsServices blueprintsServices(){
        return new BlueprintsServices();
    }

}
