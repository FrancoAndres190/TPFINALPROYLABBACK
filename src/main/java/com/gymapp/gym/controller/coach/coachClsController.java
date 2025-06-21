package com.gymapp.gym.controller.coach;

import com.gymapp.gym.persistence.dtos.Cls.ClassesDTO;
import com.gymapp.gym.persistence.dtos.Cls.CreateClsDTO;
import com.gymapp.gym.persistence.entities.Cls;
import com.gymapp.gym.service.ClsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/coach/classes")
public class coachClsController {

    @Autowired
    ClsService clsService;


    @GetMapping()
    public ResponseEntity<List<Cls>> getAll(){

        return ResponseEntity.ok(clsService.getAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Cls> getOneById(@RequestBody Long id){

        return ResponseEntity.ok(clsService.getOneById(id));

    }

    @PostMapping()
    public ResponseEntity<String> createCls(@RequestBody CreateClsDTO createClsDTO){

        return ResponseEntity.ok(clsService.createCls(createClsDTO));

    }


    //CORREGIR DE ACA PARA ABAJO............
    @PutMapping()
    public void editCls(@RequestBody ClassesDTO classesDTO) {

        clsService.editCls(classesDTO);
    }

    @DeleteMapping()
    public void deleteCls(@RequestBody ClassesDTO classesDTO) {

        clsService.deleteCls(classesDTO);

    }


}