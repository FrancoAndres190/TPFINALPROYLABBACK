package com.gymapp.gym.service;

import com.gymapp.gym.persistence.dtos.LoginDto;
import com.gymapp.gym.persistence.dtos.Usr.CreateUsrDTO;
import com.gymapp.gym.persistence.dtos.Usr.EditUsrDTO;
import com.gymapp.gym.persistence.dtos.Usr.GetUsrDTO;
import com.gymapp.gym.persistence.entities.Usr;
import com.gymapp.gym.persistence.repository.RolRepository;
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
    RolRepository rolRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<GetUsrDTO> getAll(){

        List<Usr> usrList = usrRepository.findAll();

        return usrList.stream()
                .map(this::mapUsrToGetUsrDTO)
                .collect(Collectors.toList());
    }

    private GetUsrDTO mapUsrToGetUsrDTO(Usr usr) {
        return modelMapper.map(usr, GetUsrDTO.class);
    }

    public GetUsrDTO getOneById(Long id) {
        Usr usrById = usrRepository.findById(id).orElse(null);

        return usrById == null? null : modelMapper.map(usrById, GetUsrDTO.class);
    }

    public String createUser(CreateUsrDTO createUsrDto) {

        Usr usrCreate = modelMapper.map(createUsrDto, Usr.class);

        if (usrRepository.findByEmail(usrCreate.getEmail()).isPresent()) {
            return "Email existente";
        }

        usrRepository.save(usrCreate);
        return "Creado correctamente";

    }

    public String editUser(EditUsrDTO editUsrDTO) {

        Usr searchUser = usrRepository.findByEmail(editUsrDTO.getUserEmail()).orElse(null);

        if (!usrRepository.findByEmail(editUsrDTO.getUserEmail()).isPresent()) {
            return "Email incorrecto";
        }

        if (new BCryptPasswordEncoder().matches(editUsrDTO.getOldPassword(), searchUser.getPassword())) {

            Usr usrEdit = modelMapper.map(editUsrDTO, Usr.class);
            usrEdit.setId(searchUser.getId());
            usrEdit.setEmail(searchUser.getEmail());

            usrRepository.save(usrEdit);
            return "Usuario editado correctamente";
        }

        return "Contraseña incorrecta";
    }

    public String deleteUser(Long id) {
        Usr usrById = usrRepository.findById(id).orElse(null);

        if (usrById == null) {
            return "No existe el ID";
        }

        usrRepository.delete(usrById);
        return "Eliminado correctamente";
    }



}
