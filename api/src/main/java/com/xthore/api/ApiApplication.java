package com.xthore.api;

import com.xthore.rest.RestModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import java.util.Collections;

@SpringBootApplication
@EnableFeignClients
@Import(RestModule.class)
public class ApiApplication {
    private static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ApiApplication.class);
        application.setDefaultProperties(Collections.singletonMap(
                SPRING_PROFILE_DEFAULT,
                SPRING_PROFILE_DEVELOPMENT
        ));
        application.run(args);
    }
}
