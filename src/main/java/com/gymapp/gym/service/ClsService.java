package com.gymapp.gym.service;

import com.gymapp.gym.persistence.dtos.Cls.ClassesDTO;
import com.gymapp.gym.persistence.dtos.Cls.CreateClsDTO;
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

    // Mapear Cls a DTO


    private ClassesDTO mapClsDTO(Cls cls) {

        ClassesDTO dto = new ClassesDTO();

        dto.setClassID(cls.getClassID());
        dto.setName(cls.getName());
        dto.setTimec(cls.getTimec());
        dto.setDispo(cls.getDispo());
        dto.setDescrip(cls.getDescrip());

        if (cls.getCoach() != null) {
            dto.setCoachId(cls.getCoach().getUserID());
            dto.setCoachName(cls.getCoach().getFirstName() + " " + cls.getCoach().getLastName());
        }

        return dto;
    }



    // Mapear Cls a CreateClsDTO
    private CreateClsDTO mapCreateClsDTO(Cls cls) {
        return modelMapper.map(cls, CreateClsDTO.class);
    }

    // Obtener las clases que el usuario NO tiene
    public List<ClassesDTO> getAvailableClasses(Long userId) {

        Usr usr = usrRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));

        List<Cls> allClasses = clsRepository.findAll();

        Set<Cls> userClasses = usr.getClasses();

        return allClasses.stream()
                .filter(cls -> !userClasses.contains(cls))
                .map(this::mapClsDTO)
                .collect(Collectors.toList());
    }

    // Obtener TODAS las clases (admin)
    public List<Cls> getAll() {
        return clsRepository.findAll();
    }

    // Obtener las clases de un coach
    public List<ClassesDTO> getClassesByCoachDTO(Long coachId) {

        Usr coach = usrRepository.findById(coachId)
                .orElseThrow(() -> new RuntimeException("Coach no encontrado con ID: " + coachId));

        return clsRepository.findByCoach(coach)
                .stream()
                .map(this::mapClsDTO)
                .collect(Collectors.toList());
    }


    // Obtener una clase por ID y devolver DTO
    public ClassesDTO getOneByIdDTO(Long id) {

        Cls cls = clsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + id));

        return mapClsDTO(cls); // 🚀 usar el mapper que sí agrega coachId y coachName
    }



    // Obtener una clase por ID (entidad)
    public Cls getOneById(Long id) {
        return clsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + id));
    }

    // Crear clase (asignada al coach)
    public String createCls(CreateClsDTO createClsDTO, Long coachId) {

        Usr coach = usrRepository.findById(coachId)
                .orElseThrow(() -> new RuntimeException("Coach no encontrado con ID: " + coachId));

        List<CreateClsDTO> clsList = clsRepository.findAll().stream()
                .map(this::mapCreateClsDTO)
                .toList();

        if (clsList.contains(createClsDTO)) {
            return "Ya existe una clase exactamente igual.";
        }

        Cls clsCreate = modelMapper.map(createClsDTO, Cls.class);

        // Asignamos el coach
        clsCreate.setCoach(coach);

        clsRepository.save(clsCreate);

        return "Clase " + clsCreate.getName() + " creada correctamente.";
    }

    // Editar clase
    public String editCls(ClassesDTO classesDTO) {

        Cls existingCls = clsRepository.findById(classesDTO.getClassID())
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + classesDTO.getClassID()));

        existingCls.setName(classesDTO.getName());
        existingCls.setTimec(classesDTO.getTimec());
        existingCls.setDispo(classesDTO.getDispo());
        existingCls.setDescrip(classesDTO.getDescrip());

        clsRepository.save(existingCls);

        return "Clase " + existingCls.getName() + " editada correctamente.";
    }

    // Eliminar clase (por ID)
    public String deleteCls(Long id) {

        Cls existingCls = clsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + id));

        existingCls.getUsers().forEach(user -> user.getClasses().remove(existingCls));
        existingCls.getUsers().clear();

        clsRepository.save(existingCls);  // guardar para actualizar la relación

        // Ahora sí, eliminar
        clsRepository.delete(existingCls);

        return "Clase " + existingCls.getName() + " eliminada correctamente.";
    }

}
