package com.xthore.rest.order;

import com.xthore.data.order.DataModule;
import com.xthore.domain.order.OrderModule;
import com.xthore.persistence.order.PersistenceModule;
import com.xthore.remote.order.RemoteModule;
import com.xthore.rest.RestModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import java.util.Collections;

@SpringBootApplication
@EnableFeignClients
@Import({
    RestModule.class,
    OrderModule.class,
    RemoteModule.class,
    PersistenceModule.class,
    DataModule.class
})
public class OrderApplication {
    private static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(OrderApplication.class);
        application.setDefaultProperties(Collections.singletonMap(
                SPRING_PROFILE_DEFAULT,
                SPRING_PROFILE_DEVELOPMENT
        ));
        application.run(args);
    }
}
