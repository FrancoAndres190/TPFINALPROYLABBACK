package com.gymapp.gym.controller.user;

import com.gymapp.gym.persistence.dtos.Usr.*;
import com.gymapp.gym.service.UsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/users")
public class userUsrController {

    @Autowired
    UsrService usrService;


    @PostMapping("/addclass")
    public ResponseEntity<String> addClass(@RequestBody AddClassDTO addClassDTO){

        return ResponseEntity.ok(usrService.addClass(addClassDTO));

    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUsrDTO> getOneById(@PathVariable Long id){

        //RETORNAR SU PROPIO USUARIO
        return ResponseEntity.ok(usrService.getOneById(id));

    }

    @PutMapping()
    public ResponseEntity<String> editUser(@RequestBody EditUsrDTO editUsrDTO){

        //EDITAR SOLO SU PROPIO USUARIO
        return ResponseEntity.ok(usrService.editUser(editUsrDTO));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        //ELIMINARSE SOLO
        return ResponseEntity.ok(usrService.deleteUser(id));

    }

}