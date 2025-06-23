package com.gymapp.gym.security.Impl;
import com.gymapp.gym.persistence.entities.Usr;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserDetailImpl implements UserDetails {

    private final Usr usr;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usr.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());
    }

    //Mandamos el ID por claims
    public Long getUserID() { return usr.getUserID(); }

    @Override
    public String getPassword() {
        return usr.getPassword();
    }

    @Override
    public String getUsername() {
        return usr.getEmail();
    }

    public boolean isMembershipActive(){return  usr.isMembershipActive();}


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFirstName(){
        return usr.getFirstName();
    }
}
