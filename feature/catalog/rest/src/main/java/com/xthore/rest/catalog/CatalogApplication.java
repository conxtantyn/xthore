package com.xthore.rest.catalog;

import com.xthore.data.catalog.DataModule;
import com.xthore.domain.catalog.CatalogModule;
import com.xthore.persistence.catalog.PersistenceModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import java.util.Collections;

@SpringBootApplication
@Import({
    CatalogModule.class,
    PersistenceModule.class,
    DataModule.class
})
public class CatalogApplication {
    private static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(CatalogApplication.class);
        application.setDefaultProperties(Collections.singletonMap(
                SPRING_PROFILE_DEFAULT,
                SPRING_PROFILE_DEVELOPMENT
        ));
        application.run(args);
    }
}
