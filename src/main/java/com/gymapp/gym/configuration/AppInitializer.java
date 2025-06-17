package com.gymapp.gym.configuration;

import com.gymapp.gym.service.UsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer {
    @Autowired
    UsrService usrService;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        //Esto se ejecuta al iniciar el proyecto

    }

}
