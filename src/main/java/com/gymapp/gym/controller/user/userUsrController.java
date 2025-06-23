package com.gymapp.gym.controller.user;

import com.gymapp.gym.persistence.dtos.Cls.ClassesDTO;
import com.gymapp.gym.persistence.dtos.Usr.*;
import com.gymapp.gym.service.UsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/users")
public class userUsrController {

    @Autowired
    UsrService usrService;

//-------INICIO-GET-------------------------------------------------

    // Metodo para obtener "mis clases"
    @GetMapping("/myclasses")
    public ResponseEntity<List<ClassesDTO>> getMyClasses() {
        Long userId = (Long) getUserIdFromToken();
        return ResponseEntity.ok(usrService.getMyClasses(userId));
    }

    // Metodo para obtener mis datos
    @GetMapping("/{id}")
    public ResponseEntity<GetUsrDTO> getOneById(@PathVariable Long id) {
        return ResponseEntity.ok(usrService.getOneById(id));
    }

//-------INICIO-POST-------------------------------------------------

    // Metodo para agregarnos a una clase
    @PostMapping("/addclass/{classID}")
    public ResponseEntity<String> joinClass(@PathVariable Long classID) {
        Long userId = (Long) getUserIdFromToken();
        String error = usrService.joinClass(userId, classID);

        if (error == null) {
            return ResponseEntity.ok("Te has unido a la clase.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
    }

//-------INICIO-UPDATE-------------------------------------------------

    // Metodo para editarse
    @PutMapping()
    public ResponseEntity<String> editUser(@RequestBody EditUsrDTO editUsrDTO) {
        return ResponseEntity.ok(usrService.editUser(editUsrDTO));
    }

//-------INICIO-DELETE-------------------------------------------------

    // Metodo para salirse de una clase
    @DeleteMapping("/myclasses/{classID}")
    public ResponseEntity<String> leaveClass(@PathVariable Long classID) {
        Long userId = (Long) getUserIdFromToken();
        return ResponseEntity.ok(usrService.leaveClass(userId, classID));
    }

    // Metodo para eliminar mi usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(usrService.deleteUser(id));
    }

//-------UTILS-------------------------------------------------

    // Obtener el userId del token
    private Long getUserIdFromToken() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
