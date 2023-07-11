package com.planty_app.Planty.security;

import com.planty_app.Planty.models.Utilizer;
import com.planty_app.Planty.repositories.UtilizerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UtilizerRepository utilizerRepository;
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String login){
        Utilizer utilizer=utilizerRepository.findUtilizerByLogin(login)
                .orElseThrow(()->new UsernameNotFoundException("Bad credentials"));
        return User.builder()
                .username(utilizer.getLogin())
                .password(utilizer.getCredentials().getPassword())
                .roles(utilizer.getRole().toString())
                .build();
    }
// for authorities in the future
//    private static SimpleGrantedAuthority getAuthority(Utilizer utilizer){
//        return new SimpleGrantedAuthority(string);
//    }
}
