package com.gymapp.gym.service;
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


    public void crearUsrPorDefecto() {
            System.out.println(new BCryptPasswordEncoder().encode("12345"));
    }

//-------INICIO-GET-------------------------------------------------

    //Metodo para obtener todos los usuarios
    public List<GetUsrDTO> getAll(){

        //Pedimos la lista al repositorio
        List<Usr> usrList = usrRepository.findAll();

        //Recorremos la lista y se convierte a DTO para devolverla
        return usrList.stream()
                .map(this::mapUsrToGetUsrDTO)
                .collect(Collectors.toList());

    }

    //Metodo para obtener un usuario por ID
    public GetUsrDTO getOneById(Long id) {

        //Lo pedimos al repositorio, si no existe devuelve nulo
        Usr usrById = usrRepository.findById(id).orElse(null);

        //Devuelve convertido a dto
        return usrById == null? null : modelMapper.map(usrById, GetUsrDTO.class);
    }

    //Metodo que convierte usuario a dto
    private GetUsrDTO mapUsrToGetUsrDTO(Usr usr) {
        return modelMapper.map(usr, GetUsrDTO.class);
    }


//-------INICIO-POST-------------------------------------------------

    //Metodo para crear usuario
    public String createUser(CreateUsrDTO createUsrDto) {

//        //Verificamos si existe...
//        if (usrRepository.findByEmail(usrCreate.getEmail()).isPresent()) {
//            return "Email existente";
//        }

        //Verificamos si existe...
        if (usrRepository.findByEmail(createUsrDto.getEmail()).isPresent()) {
            return "Email existente";
        }

        //Convertimos dto en user
        Usr usrCreate = modelMapper.map(createUsrDto, Usr.class);

        //Guardamos el repositorio
        usrRepository.save(usrCreate);
        return "Creado correctamente";

    }

    //Metodo para agregar un rol al usuario
    public String addRole(AddRoleDTO addRoleDTO){

        try{

            //Buscamos rol y usuario en el repositorio
            Role role = roleRepository.findById(addRoleDTO.getRoleID()).orElse(null);
            Usr usr = usrRepository.findById(addRoleDTO.getUserID()).orElse(null);

            if (role == null){return "No se ha encontrado el roleID";}
            if (usr == null){return "No se ha encontrado el usrID";}


            //usr.setRole(role);
            //Colocamos el rol nuevo
            usr.getRoles().add(role);
            usrRepository.save(usr);
            return "Usuario: " + usr.getFirstName() + " tiene el Rol " + role.getName();

        } catch (Exception e) {
            return "Error al agregar el rol al usuario. Detalles:" + e.getMessage();
        }
    }

    //Metodo para agregar una membresia al usuario
    public String addMember(AddMemberDTO addMemberDTO){

        try {

            //Verificamos si existen usuario y membresia
            MemberType memberType =
                    memberTypeRepository.findById(addMemberDTO.getMemberTypeID()).orElse(null);
            Usr usr = usrRepository.findById(addMemberDTO.getUserID()).orElse(null);

            //Checkear porque dice role
            if (memberType == null){return "No se ha encontrado el memberType";}
            if (usr == null){return "No se ha encontrado el usrID";}

            //Asignamos la membresia al usuario y guardamos
            usr.setMemberType(memberType);
            usrRepository.save(usr);
            return "Usuario: " + usr.getFirstName() + " tiene la Membresia " + memberType.getName();

        } catch (Exception e) {
            return "Error al agregar la membresia al usuario. Detalles:" + e.getMessage();
        }
    }

    //Metodo para agregar una clase al usuario
    public String addClass(AddClassDTO addClassDTO) {

        try {

            //Verificamos que existan los id
            Usr usr = usrRepository.findById(addClassDTO.getUserID()).orElseThrow(()
                    -> new RuntimeException("Usuario no encontrado con ID: " + addClassDTO.getUserID()));

            Cls cls = clsRepository.findById(addClassDTO.getClassID()).orElseThrow(()
                    -> new RuntimeException("Clase no encontrada con ID: " + addClassDTO.getClassID()));

            if (usr == null || cls == null){
                return "Error al agregar a la clase";
            }

            //Agregamos la clase al usuario y el usuario a la clase
            usr.getClasses().add(cls);
            cls.getUsers().add(usr);
            usrRepository.save(usr);
            clsRepository.save(cls);

            return "Te has agregado a la clase " + cls.getName();

        } catch (Exception e) {
            return "Error al agregar el usuario a la clase. Detalles:" + e.getMessage();
        }
    }

//-------INICIO-UPDATE-------------------------------------------------

    //Metodo para editar usuario
    public String editUser(EditUsrDTO editUsrDTO) {

        //Buscamos el usuario
        Usr searchUser = usrRepository.findByEmail(editUsrDTO.getUserEmail()).orElse(null);

        //Verificamos que exista el email
        if (!usrRepository.findByEmail(editUsrDTO.getUserEmail()).isPresent()) {
            return "Email incorrecto";
        }

        //Comparamos el hash de contraseñas (para ver si ingreso bien la contraseña)
        if (new BCryptPasswordEncoder().matches(editUsrDTO.getOldPassword(), searchUser.getPassword())) {

            //Pasamos de dto a usuario
            Usr usrEdit = modelMapper.map(editUsrDTO, Usr.class);

            //Colocamos el mismo id y email
            usrEdit.setUserID(searchUser.getUserID());
            usrEdit.setEmail(searchUser.getEmail());

            //Guardamos
            usrRepository.save(usrEdit);
            return "Usuario editado correctamente";
        }

        //Si llega hasta aca, la contraseña es incorrecta
        return "Contraseña incorrecta";
    }

//-------INICIO-DELETE-------------------------------------------------

    //Metodo para elimiar usuario (Solo administrador)
    public String deleteUser(Long id) {

        //Captamos errores
        try{

            //Buscamos el id
            Usr usrById = usrRepository.findById(id).orElse(null);

            //Si no existe
            if (usrById == null) {
                return "No existe el ID";
            }

            //Eliminamos
            usrRepository.delete(usrById);
            return "Eliminado correctamente";

        } catch (Exception e) {
            return "Error al eliminar el usuario. Detalles:" + e.getMessage();
        }
    }


}
