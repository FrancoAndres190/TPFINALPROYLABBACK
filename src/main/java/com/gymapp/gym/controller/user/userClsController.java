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



    // Obtener las clases disponibles para el usuario (las que NO tiene)
    /*@GetMapping
    public ResponseEntity<List<ClassesDTO>> getAvailableClasses() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Principal recibido: " + principal + " (type: " + principal.getClass() + ")");


        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        return ResponseEntity.ok(clsService.getAvailableClasses(userId));
    } */

    @GetMapping
    public ResponseEntity<?> getAvailableClasses() {

        try {
            // Obtener el principal (userId)
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println("Principal recibido: " + principal + " (type: " + principal.getClass() + ")");

            // Intentar parsear a Long
            Long userId = (Long) principal;
            System.out.println("UserId como Long: " + userId);

            // Llamamos al service
            List<ClassesDTO> availableClasses = clsService.getAvailableClasses(userId);

            // Devolvemos la respuesta
            return ResponseEntity.ok(availableClasses);

        } catch (Exception e) {
            // Mostramos el error en consola
            System.out.println("ERROR en getAvailableClasses: " + e.getMessage());
            e.printStackTrace();

            // Devolvemos error 500 con el mensaje
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }



}
