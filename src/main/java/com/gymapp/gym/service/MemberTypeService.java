package com.gymapp.gym.service;

import com.gymapp.gym.persistence.entities.Cls;
import com.gymapp.gym.persistence.entities.MemberType;
import com.gymapp.gym.persistence.entities.Usr;
import com.gymapp.gym.persistence.repository.MemberTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.rmi.server.LogStream.log;

@Service
public class MemberTypeService {

    @Autowired
    MemberTypeRepository memberTypeRepository;

    public List<MemberType> getAll(){
        return memberTypeRepository.findAll();
    }
    public MemberType getOneById(Long id) {return memberTypeRepository.findById(id).orElse(null);}

    //Agregar verificacion de crear si no existe uno igual
    public void createMemberType(MemberType memberType) {memberTypeRepository.save(memberType);}

    //Agregar verificacion para editar solo si existe
    public void editMemberType(MemberType memberType) {memberTypeRepository.save(memberType);}

    //Agregar verificacion de eliminar solo si existe
    public void deleteMemberType(MemberType memberType) {memberTypeRepository.delete(memberType);}

}