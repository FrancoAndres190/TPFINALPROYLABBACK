package com.gymapp.gym.service;

import com.gymapp.gym.persistence.dtos.Cls.ClassesDTO;
import com.gymapp.gym.persistence.dtos.Cls.CreateClsDTO;
import com.gymapp.gym.persistence.dtos.Usr.UserSimpleDTO;
import com.gymapp.gym.persistence.entities.Cls;
import com.gymapp.gym.persistence.entities.Usr;
import com.gymapp.gym.persistence.repository.ClsRepository;
import com.gymapp.gym.persistence.repository.UsrRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String removeUserFromClass(Long classId, Long userId) {

        Cls cls = clsRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + classId));

        Usr usr = usrRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));

        if (!cls.getUsers().contains(usr)) {
            return "El usuario no pertenece a esta clase.";
        }

        cls.getUsers().remove(usr);
        usr.getClasses().remove(cls);

        clsRepository.save(cls);
        usrRepository.save(usr);

        return "Usuario eliminado de la clase correctamente.";
    }

    private ClassesDTO mapClsDTO(Cls cls) {

        ClassesDTO dto = new ClassesDTO();

        dto.setClassID(cls.getClassID());
        dto.setName(cls.getName());
        dto.setTimec(cls.getTimec());
        dto.setDispo(cls.getDispo());
        dto.setDescrip(cls.getDescrip());

        dto.setMaxCapacity(cls.getMaxCapacity());
        dto.setDurationMinutes(cls.getDurationMinutes());

        if (cls.getCreatedAt() != null) {
            dto.setCreatedAt(cls.getCreatedAt());
        }

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

        List<Cls> allClasses = clsRepository.findAllByOrderByCreatedAtDesc();

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

        return clsRepository.findByCoachOrderByCreatedAtDesc(coach)
                .stream()
                .map(this::mapClsDTO)
                .collect(Collectors.toList());
    }




    // Obtener una clase por ID (entidad)
    public Cls getOneById(Long id) {
        return clsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + id));
    }

    public List<UserSimpleDTO> getListUsersInClass(Long id) {
        return getOneById(id).getUsers().stream()
                .map(user -> modelMapper.map(user, UserSimpleDTO.class))
                .toList();
    }

    // Crear clase (asignada al coach)
    public ClassesDTO createCls(CreateClsDTO createClsDTO, Long coachId) {

        Usr coach = usrRepository.findById(coachId)
                .orElseThrow(() -> new RuntimeException("Coach no encontrado con ID: " + coachId));

        List<CreateClsDTO> clsList = clsRepository.findAll().stream()
                .map(this::mapCreateClsDTO)
                .toList();

        if (clsList.contains(createClsDTO)) {
            throw new RuntimeException("Ya existe una clase exactamente igual.");
        }

        Cls clsCreate = modelMapper.map(createClsDTO, Cls.class);

        // Asignamos el coach
        clsCreate.setCoach(coach);

        clsRepository.save(clsCreate);


        return mapClsDTO(clsCreate);
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
