package com.gymapp.gym.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**");
//    }


//registry.addMapping("/**")
//        .allowedOrigins("http://localhost:5173")
//        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//        .allowedHeaders("*")
//        .allowCredentials(true);

@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {



    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    //Redirigir todas las solicitudes al index.html
    //@Override
    //public void addViewControllers(ViewControllerRegistry registry) {

        //registry.addViewController("/{spring:\\w+}").setViewName("forward:/");
        //registry.addViewController("/**/{spring:\\w+}").setViewName("forward:/");
        //registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}").setViewName("forward:/");
    //}

}
