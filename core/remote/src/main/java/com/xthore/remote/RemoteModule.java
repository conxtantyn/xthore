package com.xthore.remote;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { RemoteModule.CONFIGURATION })
public class RemoteModule {
    public static final String CONFIGURATION = "com.xthore.remote.config";
}
