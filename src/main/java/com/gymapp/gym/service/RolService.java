package com.gymapp.gym.service;

import com.gymapp.gym.persistence.entities.Role;
import com.gymapp.gym.persistence.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    RoleRepository roleRepository;


    // Metodo para obtener todos los roles
    public List<Role> getAll() { return roleRepository.findAll(); }


    // Metodo para obtener un rol por ID
    public Role getOneById(Long id) { return roleRepository.findById(id).orElse(null); }


    // Metodo para crear un rol (si no existe uno igual)
    public void createRol(Role role) {
        Optional<Role> existingRole = roleRepository.findByName(role.getName());
        if (existingRole.isEmpty()) {
            roleRepository.save(role);
        } else {
            throw new RuntimeException("El rol ya existe");
        }
    }


    // Metodo para editar un rol (solo si existe)
    public void editRol(Role role) {
        if (role.getRoleID() != null && roleRepository.existsById(role.getRoleID())) {
            roleRepository.save(role);
        } else {
            throw new RuntimeException("El rol no existe");
        }
    }


    // Metodo para eliminar un rol (solo si existe)
    public void deleteRol(Role role) {
        if (role.getRoleID() != null && roleRepository.existsById(role.getRoleID())) {
            roleRepository.delete(role);
        } else {
            throw new RuntimeException("El rol no existe");
        }
    }


}
