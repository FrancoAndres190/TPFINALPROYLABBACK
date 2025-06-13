package com.gymapp.gym.service;

import com.gymapp.gym.persistence.dtos.Cls.ClassesDTO;
import com.gymapp.gym.persistence.dtos.Cls.CreateClsDTO;
import com.gymapp.gym.persistence.dtos.Usr.AddClassDTO;
import com.gymapp.gym.persistence.dtos.Usr.GetUsrDTO;
import com.gymapp.gym.persistence.entities.Cls;
import com.gymapp.gym.persistence.entities.Usr;
import com.gymapp.gym.persistence.repository.ClsRepository;
import com.gymapp.gym.persistence.repository.UsrRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClsService {
    @Autowired
    ClsRepository clsRepository;

    @Autowired
    ModelMapper modelMapper;




    private ClassesDTO mapClsDTO(Cls cls) {

        return modelMapper.map(cls, ClassesDTO.class);

    }

    public List<ClassesDTO> getAllDTO(){

        List<Cls> clsList = clsRepository.findAll();

        return clsList.stream()
                .map(this::mapClsDTO)
                .collect(Collectors.toList());

    }

    public List<Cls> getAll(){
        return clsRepository.findAll();
    }



    public ClassesDTO getOneByIdDTO(Long id) {

        Cls cls = clsRepository.findById(id).orElse(null);

        return cls == null ? null : modelMapper.map(cls, ClassesDTO.class);

    }

    public Cls getOneById(Long id) {

        return clsRepository.findById(id).orElse(null);

    }


    private CreateClsDTO mapCreateClsDTO(Cls cls) {

        return modelMapper.map(cls, CreateClsDTO.class);

    }

    //Agregar verificacion de crear si no existe uno igual
    public String createCls(CreateClsDTO createClsDTO) {


        List<CreateClsDTO> clsList = clsRepository.findAll().stream()
                .map(this::mapCreateClsDTO)
                .toList();

        if (clsList.contains(createClsDTO)) {
            return "Ya existe una clase exactamente igual.";
        }

        Cls clsCreate = modelMapper.map(createClsDTO, Cls.class);

        clsRepository.save(clsCreate);
        return "Clase " + clsCreate.getName() + " correctamente";

    }

    //Agregar verificacion para editar solo si existe
    public void editCls(Cls cls) {clsRepository.save(cls);}

    //Agregar verificacion de eliminar solo si existe
    public void deleteCls(Cls cls) {clsRepository.delete(cls);}


}

