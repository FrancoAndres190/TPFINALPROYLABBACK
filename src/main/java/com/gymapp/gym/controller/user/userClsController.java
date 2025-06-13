package com.gymapp.gym.controller.user;

import com.gymapp.gym.persistence.dtos.Cls.ClassesDTO;
import com.gymapp.gym.service.ClsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user/classes")
public class userClsController {

    @Autowired
    ClsService clsService;


    @GetMapping()
    public ResponseEntity<List<ClassesDTO>> getAllDTO(){

        return ResponseEntity.ok(clsService.getAllDTO());

    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassesDTO> getOneByIdDTO(@RequestBody Long id){

        return ResponseEntity.ok(clsService.getOneByIdDTO(id));

    }

}