package com.gymapp.gym.service;

import com.gymapp.gym.persistence.entities.Cls;
import com.gymapp.gym.persistence.repository.ClsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClsService {
    @Autowired
    ClsRepository clsRepository;


    public List<Cls> getAll(){return clsRepository.findAll();}

    public Cls getOneById(Long id) {return clsRepository.findById(id).orElse(null);}

    //Agregar verificacion de crear si no existe uno igual
    public void createCls(Cls cls) {clsRepository.save(cls);}

    //Agregar verificacion para editar solo si existe
    public void editCls(Cls cls) {clsRepository.save(cls);}

    //Agregar verificacion de eliminar solo si existe
    public void deleteCls(Cls cls) {clsRepository.delete(cls);}


}

