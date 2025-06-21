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
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ClsService {
    @Autowired
    ClsRepository clsRepository;

    @Autowired
    UsrRepository usrRepository;

    @Autowired
    ModelMapper modelMapper;





    private ClassesDTO mapClsDTO(Cls cls) {

        return modelMapper.map(cls, ClassesDTO.class);

    }

    public List<ClassesDTO> getAvailableClasses(Long userId) {

        // Obtenemos el usuario
        Usr usr = usrRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));

        // Todas las clases
        List<Cls> allClasses = clsRepository.findAll();

        // Clases que YA tiene el usuario
        Set<Cls> userClasses = usr.getClasses();

        // Filtramos: devolvemos solo las clases que el usuario NO tiene
        return allClasses.stream()
                .filter(cls -> !userClasses.contains(cls))
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

    public void editCls(ClassesDTO classesDTO) {

        Cls existingCls = clsRepository.findById(classesDTO.getClassID())
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + classesDTO.getClassID()));

        // Solo actualizamos campos simples
        existingCls.setName(classesDTO.getName());
        existingCls.setTimec(classesDTO.getTimec());
        existingCls.setDispo(classesDTO.getDispo());
        existingCls.setDescrip(classesDTO.getDescrip());

        clsRepository.save(existingCls);
    }

    public void deleteCls(ClassesDTO classesDTO) {

        Cls existingCls = clsRepository.findById(classesDTO.getClassID())
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + classesDTO.getClassID()));

        clsRepository.delete(existingCls);
    }



}

