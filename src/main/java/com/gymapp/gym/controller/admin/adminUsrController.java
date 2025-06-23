package com.gymapp.gym.controller.admin;
import com.gymapp.gym.persistence.dtos.Usr.*;
import com.gymapp.gym.persistence.entities.Role;
import com.gymapp.gym.service.RolService;
import com.gymapp.gym.service.UsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//Controlador para administradores
@RestController
@RequestMapping("/admin/users")
public class adminUsrController {

    @Autowired
    UsrService usrService;

    @Autowired
    RolService rolService;


//-------INICIO-GET-------------------------------------------------

    //Metodo para obtener todos los usuarios
    @GetMapping()
    public ResponseEntity<List<GetUsrDTO>> getAll(){

        return ResponseEntity.ok(usrService.getAll());

    }

    //Metodo para obtener todos los roles
    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return rolService.getAll();
    }

    //Metodo para obtener un usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<GetUsrDTO> getOneById(@PathVariable Long id){

            return ResponseEntity.ok(usrService.getOneById(id));

    }

//-------INICIO-POST-------------------------------------------------

    //Metodo para agregar un rol a un usuario
    @PostMapping("/addrole")
    public ResponseEntity<String> addRole(@RequestBody AddRoleDTO addRoleDTO){

        return ResponseEntity.ok(usrService.addRole(addRoleDTO));

    }

    //Metodo para agregar una membresia al usuario
    @PostMapping("/addmember")
    public ResponseEntity<String> addMember(@RequestBody AddMemberDTO addMemberDTO){

        return ResponseEntity.ok(usrService.addMember(addMemberDTO));

    }

    // Metodo para registrar un pago de mensualidad
    @PostMapping("/pay")
    public ResponseEntity<String> payMembership(@RequestBody PayMembershipDTO payMembershipDTO) {
        return ResponseEntity.ok(usrService.payMembership(payMembershipDTO));
    }


//-------INICIO-DELETE-------------------------------------------------

    //Metodo para eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        return ResponseEntity.ok(usrService.deleteUser(id));
    }

//-------INICIO-UPDATE-------------------------------------------------

    //Metodo para actualizar un usuario
    @PutMapping()
    public ResponseEntity<String> editUser(@RequestBody EditUsrDTO editUsrDTO){

        return ResponseEntity.ok(usrService.editUser(editUsrDTO));

    }





}