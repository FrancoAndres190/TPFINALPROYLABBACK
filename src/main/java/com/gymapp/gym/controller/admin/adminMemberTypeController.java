package com.gymapp.gym.controller.admin;

import com.gymapp.gym.persistence.entities.MemberType;
import com.gymapp.gym.service.MemberTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/MembershipType")
public class adminMemberTypeController {

    @Autowired
    MemberTypeService memberTypeService;


    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MemberType>> getAll(){//<String> getAll(){
        //return ResponseEntity.ok(memberTypeService.checkUserRoles());
        return ResponseEntity.ok(memberTypeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberType> getOneById(@RequestBody Long id){return ResponseEntity.ok(memberTypeService.getOneById(id));}

    @PostMapping()
    public void createMemberType(@RequestBody MemberType memberType){memberTypeService.createMemberType(memberType);}

    @PutMapping()
    public void editMemberType(@RequestBody MemberType memberType) {memberTypeService.editMemberType(memberType);}

    @DeleteMapping()
    public void deleteMemberType(@RequestBody MemberType memberType) {memberTypeService.deleteMemberType(memberType);}
}
