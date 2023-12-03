package com.gymapp.gym.controller;

import com.gymapp.gym.persistence.dtos.Usr.CreateUsrDTO;
import com.gymapp.gym.persistence.dtos.Usr.EditUsrDTO;
import com.gymapp.gym.persistence.dtos.Usr.GetUsrDTO;
import com.gymapp.gym.persistence.entities.Usr;
import com.gymapp.gym.service.UsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/users")
public class UsrController {

    @Autowired
    UsrService usrService;

    @GetMapping()
    public ResponseEntity<List<GetUsrDTO>> getAll(){return ResponseEntity.ok(usrService.getAll());}

    @GetMapping("/{id}")
    public ResponseEntity<GetUsrDTO> getOneById(@PathVariable Long id){
        return ResponseEntity.ok(usrService.getOneById(id));
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody CreateUsrDTO createUsrDto){

        return ResponseEntity.ok(usrService.createUser(createUsrDto));
    }

    @PutMapping()
    public ResponseEntity<?> editUser(@RequestBody EditUsrDTO editUsrDTO){

        return ResponseEntity.ok(usrService.editUser(editUsrDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(usrService.deleteUser(id));
    }



    /* @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody LoginDto usr){

        return ResponseEntity.ok(usrService.userLogin(usr));

    }

    @PostMapping("/crear")
    public void crear(@RequestBody Usr usr){

        usrService.guardarUsr(usr);
    }


     */

}