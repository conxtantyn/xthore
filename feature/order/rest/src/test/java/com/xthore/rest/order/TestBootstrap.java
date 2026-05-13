package com.xthore.rest.order;

import com.xthore.data.order.DataModule;
import com.xthore.domain.order.OrderModule;
import com.xthore.persistence.order.PersistenceModule;
import com.xthore.remote.order.RemoteModule;
import com.xthore.rest.RestModule;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SpringBootTest(
        classes = OrderApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.main.allow-bean-definition-overriding=true",
                "spring.cloud.config.enabled=false",
                "spring.cloud.bootstrap.enabled=false",
                "eureka.client.enabled=false"
        }
)
@Import({
        RestModule.class,
        OrderModule.class,
        RemoteModule.class,
        PersistenceModule.class,
        DataModule.class
})
@ComponentScan(basePackages = {"com.xthore"})
@AutoConfigureWebTestClient
@EnableAutoConfiguration
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestBootstrap {}
