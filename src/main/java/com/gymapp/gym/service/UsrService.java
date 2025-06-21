package com.gymapp.gym.service;

import com.gymapp.gym.exception.NotFoundException;
import com.gymapp.gym.persistence.dtos.Cls.ClassesDTO;
import com.gymapp.gym.persistence.dtos.Usr.*;
import com.gymapp.gym.persistence.entities.Cls;
import com.gymapp.gym.persistence.entities.MemberType;
import com.gymapp.gym.persistence.entities.Role;
import com.gymapp.gym.persistence.entities.Usr;
import com.gymapp.gym.persistence.repository.ClsRepository;
import com.gymapp.gym.persistence.repository.MemberTypeRepository;
import com.gymapp.gym.persistence.repository.RoleRepository;
import com.gymapp.gym.persistence.repository.UsrRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.List;

@Service
public class UsrService {

    @Autowired
    UsrRepository usrRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ClsRepository clsRepository;
    @Autowired
    MemberTypeRepository memberTypeRepository;
    @Autowired
    ModelMapper modelMapper;

    // Devuelve las clases del usuario autenticado
    public List<ClassesDTO> getMyClasses(Long userId) {
        Usr usr = usrRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + userId));

        return usr.getClasses().stream()
                .map(cls -> modelMapper.map(cls, ClassesDTO.class))
                .collect(Collectors.toList());
    }

    // Devuelve todos los usuarios registrados
    public List<GetUsrDTO> getAll() {
        List<Usr> usrList = usrRepository.findAll();

        return usrList.stream()
                .map(this::mapUsrToGetUsrDTO)
                .collect(Collectors.toList());
    }

    // Devuelve un usuario por su ID
    public GetUsrDTO getOneById(Long id) {
        Usr usrById = usrRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + id));

        return modelMapper.map(usrById, GetUsrDTO.class);
    }

    // Crea un nuevo usuario
    public String createUser(CreateUsrDTO createUsrDto) {

        if (usrRepository.findByEmail(createUsrDto.getEmail()).isPresent()) {
            return "Email existente";
        }

        Usr usrCreate = modelMapper.map(createUsrDto, Usr.class);
        usrCreate.setPassword(new BCryptPasswordEncoder().encode(createUsrDto.getPassword()));

        usrRepository.save(usrCreate);
        return "Creado correctamente";
    }

    // Agrega un rol a un usuario
    public String addRole(AddRoleDTO addRoleDTO) {

        Role role = roleRepository.findById(addRoleDTO.getRoleID())
                .orElseThrow(() -> new NotFoundException("No se ha encontrado el roleID"));

        Usr usr = usrRepository.findById(addRoleDTO.getUserID())
                .orElseThrow(() -> new NotFoundException("No se ha encontrado el usrID"));

        if (usr.getRoles().contains(role)) {
            return "El usuario ya tiene el rol " + role.getName();
        }

        usr.getRoles().add(role);
        usrRepository.save(usr);

        return "Usuario: " + usr.getFirstName() + " tiene el Rol " + role.getName();
    }

    // Asigna una membresía a un usuario
    public String addMember(AddMemberDTO addMemberDTO) {

        MemberType memberType = memberTypeRepository.findById(addMemberDTO.getMemberTypeID())
                .orElseThrow(() -> new NotFoundException("No se ha encontrado la membresía"));

        Usr usr = usrRepository.findById(addMemberDTO.getUserID())
                .orElseThrow(() -> new NotFoundException("No se ha encontrado el usrID"));

        usr.setMemberType(memberType);
        usrRepository.save(usr);

        return "Usuario: " + usr.getFirstName() + " tiene la Membresía " + memberType.getName();
    }

    // Inscribe al usuario autenticado en una clase
    public String joinClass(Long userId, Long classId) {

        Usr usr = usrRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + userId));

        Cls cls = clsRepository.findById(classId)
                .orElseThrow(() -> new NotFoundException("Clase no encontrada con ID: " + classId));

        if (usr.getClasses().contains(cls)) {
            return "Ya estás unido a la clase: " + cls.getName();
        }

        usr.getClasses().add(cls);
        cls.getUsers().add(usr);

        usrRepository.save(usr);
        clsRepository.save(cls);

        return "Te has unido a la clase: " + cls.getName();
    }

    // Modifica los datos del usuario autenticado
    public String editUser(EditUsrDTO editUsrDTO) {

        Usr searchUser = usrRepository.findByEmail(editUsrDTO.getUserEmail())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con email: " + editUsrDTO.getUserEmail()));

        // Verificar contraseña
        if (new BCryptPasswordEncoder().matches(editUsrDTO.getOldPassword(), searchUser.getPassword())) {

            // Si viene un nombre, actualizarlo
            if (editUsrDTO.getFirstName() != null && !editUsrDTO.getFirstName().isBlank()) {
                searchUser.setFirstName(editUsrDTO.getFirstName());
            }

            // Si viene un apellido, actualizarlo
            if (editUsrDTO.getLastName() != null && !editUsrDTO.getLastName().isBlank()) {
                searchUser.setLastName(editUsrDTO.getLastName());
            }

            // Si viene una nueva contraseña, actualizarla
            if (editUsrDTO.getPassword() != null && !editUsrDTO.getPassword().isBlank()) {
                searchUser.setPassword(new BCryptPasswordEncoder().encode(editUsrDTO.getPassword()));
            }

            usrRepository.save(searchUser);
            return "Usuario editado correctamente";
        }

        return "Contraseña incorrecta";
    }


    // Elimina un usuario (solo administrador)
    public String deleteUser(Long id) {

        Usr usrById = usrRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + id));

        usrRepository.delete(usrById);
        return "Eliminado correctamente";
    }

    // Da de baja al usuario autenticado de una clase
    public String leaveClass(Long userId, Long classId) {

        Usr usr = usrRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + userId));

        Cls cls = clsRepository.findById(classId)
                .orElseThrow(() -> new NotFoundException("Clase no encontrada con ID: " + classId));

        if (!usr.getClasses().contains(cls)) {
            return "No estabas anotado en la clase: " + cls.getName();
        }

        usr.getClasses().remove(cls);
        cls.getUsers().remove(usr);

        usrRepository.save(usr);
        clsRepository.save(cls);

        return "Te has dado de baja de la clase: " + cls.getName();
    }

    // Mapea usuario a DTO
    private GetUsrDTO mapUsrToGetUsrDTO(Usr usr) {
        return modelMapper.map(usr, GetUsrDTO.class);
    }
}
