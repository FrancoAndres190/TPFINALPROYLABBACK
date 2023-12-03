package com.gymapp.gym.service;

import com.gymapp.gym.persistence.entities.Cls;
import com.gymapp.gym.persistence.entities.Rol;
import com.gymapp.gym.persistence.repository.ClsRepository;
import com.gymapp.gym.persistence.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {
    @Autowired
    RolRepository rolRepository;

    public List<Rol> getAll(){return rolRepository.findAll();}

    public Rol getOneById(Long id) {return rolRepository.findById(id).orElse(null);}

    //Agregar verificacion de crear si no existe uno igual
    public void createRol(Rol rol) {rolRepository.save(rol);}

    //Agregar verificacion para editar solo si existe
    public void editRol(Rol rol) {rolRepository.save(rol);}

    //Agregar verificacion de eliminar solo si existe
    public void deleteRol(Rol rol) {rolRepository.delete(rol);}


}