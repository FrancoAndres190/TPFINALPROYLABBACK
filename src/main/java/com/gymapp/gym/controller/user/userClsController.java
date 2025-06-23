package com.gymapp.gym.controller.user;

import com.gymapp.gym.persistence.dtos.Cls.ClassesDTO;
import com.gymapp.gym.service.ClsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/classes")
public class userClsController {

    @Autowired
    ClsService clsService;


//-------INICIO-GET-------------------------------------------------

    //Metodo que devuelve todas las clases (Saca las que ya estas anotado)
    @GetMapping
    public ResponseEntity<?> getAvailableClasses() {

        try {

            Object principal = getUserIdFromToken();
            Long userId = (Long) principal;
            List<ClassesDTO> availableClasses = clsService.getAvailableClasses(userId);

            return ResponseEntity.ok(availableClasses);

        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }


//-------UTILS-------------------------------------------------

    // Obtener el userId del token
    private Long getUserIdFromToken() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
