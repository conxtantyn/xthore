package com.xthore.rest.catalog;
 
import com.xthore.data.catalog.DataModule;
import com.xthore.domain.catalog.CatalogModule;
import com.xthore.persistence.catalog.PersistenceModule;
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
    classes = CatalogApplication.class,
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
        PersistenceModule.class,
        DataModule.class,
        CatalogModule.class
})
@ComponentScan(basePackages = {"com.xthore"})
@AutoConfigureWebTestClient
@EnableAutoConfiguration
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestBootstrap {}
