package com.gymapp.gym.controller.coach;

import com.gymapp.gym.persistence.dtos.Cls.ClassesDTO;
import com.gymapp.gym.persistence.dtos.Cls.CreateClsDTO;
import com.gymapp.gym.persistence.dtos.Usr.UserSimpleDTO;
import com.gymapp.gym.persistence.entities.Cls;
import com.gymapp.gym.service.ClsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/coach/classes")
public class coachClsController {

    @Autowired
    ClsService clsService;


    //Eliminar un usuario de la clase
    @DeleteMapping("/userlist/{classId}/user/{userId}")
    public ResponseEntity<String> removeUserFromClass(
            @PathVariable Long classId,
            @PathVariable Long userId) {

        return ResponseEntity.ok(clsService.removeUserFromClass(classId, userId));
    }

    // Obtener las clases del coach logueado
    @GetMapping
    public ResponseEntity<List<ClassesDTO>> getClassesByCoach() {
        Long coachId = getUserIdFromToken();
        return ResponseEntity.ok(clsService.getClassesByCoachDTO(coachId));
    }

    // Obtener una clase por ID
    @GetMapping("/userlist/{id}")
    public ResponseEntity<List<UserSimpleDTO>> getUsersInClass(@PathVariable Long id) {

        return ResponseEntity.ok(clsService.getListUsersInClass(id));
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
