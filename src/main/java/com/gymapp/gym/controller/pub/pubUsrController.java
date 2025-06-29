package com.gymapp.gym.controller.pub;

import com.gymapp.gym.persistence.dtos.Usr.CreateUsrDTO;
import com.gymapp.gym.service.UsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pub/users")
public class pubUsrController {

    @Autowired
    UsrService usrService;

//-------INICIO-POST-------------------------------------------------

    // Metodo para crear usuario
    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody CreateUsrDTO createUsrDto) {

        String error = usrService.createUser(createUsrDto);

        if (error == null) {
            return ResponseEntity.ok("Usuario creado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
    }


}