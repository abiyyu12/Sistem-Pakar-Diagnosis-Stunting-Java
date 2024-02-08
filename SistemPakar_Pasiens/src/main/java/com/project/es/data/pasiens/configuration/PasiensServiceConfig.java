package com.project.es.data.pasiens.configuration;

import com.project.es.data.libary.entity.Admin;
import com.project.es.data.libary.entity.Pasiens;
import com.project.es.data.libary.repository.AdminRepository;
import com.project.es.data.libary.repository.PasiensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

public class PasiensServiceConfig implements UserDetailsService {
    @Autowired
    private PasiensRepository pasiensRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Pasiens pasiens = pasiensRepository.findByEmail(username);
        if(pasiens == null){
            throw new UsernameNotFoundException("Could Not Find Username");
        }
        return new User(
                pasiens.getEmail(),
                pasiens.getPassword(),
                pasiens.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }
}
