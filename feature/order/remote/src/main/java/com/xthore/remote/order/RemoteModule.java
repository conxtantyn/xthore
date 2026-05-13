package com.xthore.remote.order;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration("orderRemoteModule")
@Import(com.xthore.remote.RemoteModule.class)
@EnableFeignClients("com.xthore.remote.order.client")
@ComponentScan("com.xthore.remote.order.api")
public class RemoteModule {}
