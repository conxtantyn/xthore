package com.xthore.data.order;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.xthore.data.order.repository",
        "com.xthore.data.order.interactor",
})
public class DataModule {}
