package com.example.demo.security;

import com.example.demo.UserComponent.Domain.Entities.Enums.RoleType;
import com.example.demo.UserComponent.Domain.Entities.User;
import com.example.demo.UserComponent.Repositories.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username invalid"));
        Collection<GrantedAuthority> grantedAuthorities = mapRoleToAuthority(user.getRole());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    private Collection<GrantedAuthority> mapRoleToAuthority(RoleType role){
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }
}
