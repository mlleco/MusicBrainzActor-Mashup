package com.hajjimlle.musicbrainz;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import akka.actor.ActorSystem;

import static com.hajjimlle.musicbrainz.SpringExtension.SpringExtProvider;


@Configuration
public class AkkaMashupConfiguration {

     @Autowired
    private ApplicationContext applicationContext;


    @Bean
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("AkkaJavaSpring");
        SpringExtProvider.get(system).initialize(applicationContext);
        return system;
    }

    @Bean(name="restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    Executor executor() {
        return Executors.newFixedThreadPool(20);
    }
}
