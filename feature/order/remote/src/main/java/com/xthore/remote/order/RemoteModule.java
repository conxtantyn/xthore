package com.xthore.remote.order;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.xthore.remote.order.api")
@Import(com.xthore.remote.RemoteModule.class)
public class RemoteModule { }
