package com.gymapp.gym.service;

import com.gymapp.gym.persistence.entities.Reserv;
import com.gymapp.gym.persistence.repository.ReservRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservService {

    @Autowired
    ReservRepository reservRepository;

    public List<Reserv> getAll(){return  reservRepository.findAll();}

    public Reserv getOneById(Long id) {return reservRepository.findById(id).orElse(null);}

    //Agregar verificacion de crear si no existe uno igual
    public void createReserv(Reserv reserv) {reservRepository.save(reserv);}

    //Agregar verificacion para editar solo si existe
    public void editReserv(Reserv reserv) {reservRepository.save(reserv);}

    //Agregar verificacion de eliminar solo si existe
    public void deleteReserv(Reserv reserv) {reservRepository.delete(reserv);}

}