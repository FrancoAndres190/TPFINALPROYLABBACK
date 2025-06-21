package com.gymapp.gym.controller.user;

import com.gymapp.gym.persistence.dtos.Cls.ClassesDTO;
import com.gymapp.gym.persistence.dtos.Usr.*;
import com.gymapp.gym.service.UsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/users")
public class userUsrController {

    @Autowired
    UsrService usrService;


    @GetMapping("/myclasses")
    public ResponseEntity<List<ClassesDTO>> getMyClasses() {


        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(usrService.getMyClasses(userId));
    }
    @DeleteMapping("/myclasses/{classID}")
    public ResponseEntity<String> leaveClass(@PathVariable Long classID) {

        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(usrService.leaveClass(userId, classID));
    }


    //Nos agregamos a una clase
    @PostMapping("/addclass/{classID}")
    public ResponseEntity<String> joinClass(@PathVariable Long classID) {

        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(usrService.joinClass(userId, classID));
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