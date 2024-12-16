package com.example.demo.security.ExceptionHandling;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username invalid"));
        Collection<GrantedAuthority> grantedAuthorities = mapRoleToAuthority(user);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    private Collection<GrantedAuthority> mapRoleToAuthority(User user){
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority(user.getRole().name()));
        if(user.getBusiness() != null && user.getBusiness().getId() != null){
            collection.add(new SimpleGrantedAuthority("BUSINESS_" + user.getBusiness().getId()));
        }
        return collection;
    }
}
