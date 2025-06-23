package com.gymapp.gym.security.Impl;

import com.gymapp.gym.persistence.entities.Usr;
import com.gymapp.gym.persistence.repository.UsrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UsrRepository usrRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usr usr = usrRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException
                        ("El usuario con email " + email + " no existe"));

        return new UserDetailImpl(usr);

    }
}
