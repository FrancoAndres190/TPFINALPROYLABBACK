package com.gymapp.gym.controller;

import com.gymapp.gym.persistence.entities.Reserv;
import com.gymapp.gym.service.ReservService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservController {

    @Autowired
    ReservService reservService;

    @GetMapping()
    public ResponseEntity<List<Reserv>> getAll(){return ResponseEntity.ok(reservService.getAll());}

    @GetMapping("/{id}")
    public ResponseEntity<Reserv> getOneById(@RequestBody Long id){return ResponseEntity.ok(reservService.getOneById(id));}

    @PostMapping()
    public void createReserv(@RequestBody Reserv reserv){reservService.createReserv(reserv);}

    @PutMapping()
    public void editReserv(@RequestBody Reserv reserv) {reservService.editReserv(reserv);}

    @DeleteMapping()
    public void deleteReserv(@RequestBody Reserv reserv) {reservService.deleteReserv(reserv);}

}

