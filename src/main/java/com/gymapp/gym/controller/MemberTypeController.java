package com.gymapp.gym.controller;

import com.gymapp.gym.persistence.entities.Cls;
import com.gymapp.gym.service.ClsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/MembershipType")
public class MemberTypeController {

    @Autowired
    ClsService clsService;


    @GetMapping()
    public ResponseEntity<List<Cls>> getAll(){return ResponseEntity.ok(clsService.getAll());}

    @GetMapping("/{id}")
    public ResponseEntity<Cls> getOneById(@RequestBody Long id){return ResponseEntity.ok(clsService.getOneById(id));}

    @PostMapping()
    public void createCls(@RequestBody Cls cls){clsService.createCls(cls);}

    @PutMapping()
    public void editCls(@RequestBody Cls cls) {clsService.editCls(cls);}

    @DeleteMapping()
    public void deleteCls(@RequestBody Cls cls) {clsService.deleteCls(cls);}
}
