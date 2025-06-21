package com.gymapp.gym.controller.coach;

import com.gymapp.gym.persistence.dtos.Cls.ClassesDTO;
import com.gymapp.gym.persistence.dtos.Cls.CreateClsDTO;
import com.gymapp.gym.persistence.entities.Cls;
import com.gymapp.gym.service.ClsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coach/classes")
public class coachClsController {

    @Autowired
    ClsService clsService;

    // Obtener las clases del coach logueado
    @GetMapping
    public ResponseEntity<List<ClassesDTO>> getClassesByCoach() {
        Long coachId = getUserIdFromToken();
        return ResponseEntity.ok(clsService.getClassesByCoachDTO(coachId));
    }

    // Obtener una clase por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cls> getOneById(@PathVariable Long id) {
        return ResponseEntity.ok(clsService.getOneById(id));
    }

    // Crear una nueva clase (asignada al coach logueado)
    @PostMapping
    public ResponseEntity<ClassesDTO> createCls(@RequestBody CreateClsDTO createClsDTO) {

        Long coachId = getUserIdFromToken();

        ClassesDTO newClassDTO = clsService.createCls(createClsDTO, coachId);
        return ResponseEntity.ok(newClassDTO);
    }


    // Editar una clase existente
    @PutMapping
    public ResponseEntity<String> editCls(@RequestBody ClassesDTO classesDTO) {
        clsService.editCls(classesDTO);
        return ResponseEntity.ok("Clase editada correctamente");
    }

    // Eliminar una clase por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCls(@PathVariable Long id) {
        clsService.deleteCls(id);
        return ResponseEntity.ok("Clase eliminada correctamente");
    }

    // Obtener el userId del token JWT
    private Long getUserIdFromToken() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
