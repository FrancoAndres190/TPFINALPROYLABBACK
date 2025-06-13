package com.gymapp.gym.service;

import com.gymapp.gym.persistence.entities.Role;
import com.gymapp.gym.persistence.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {
    @Autowired
    RoleRepository roleRepository;

    public List<Role> getAll(){return roleRepository.findAll();}

    public Role getOneById(Long id) {return roleRepository.findById(id).orElse(null);}

    //Agregar verificacion de crear si no existe uno igual
    public void createRol(Role role) {
        roleRepository.save(role);}

    //Agregar verificacion para editar solo si existe
    public void editRol(Role role) {
        roleRepository.save(role);}

    //Agregar verificacion de eliminar solo si existe
    public void deleteRol(Role role) {
        roleRepository.delete(role);}


}