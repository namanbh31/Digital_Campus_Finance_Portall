package com.digitalFinancePortal.config;

import com.digitalFinancePortal.entities.User;
import com.digitalFinancePortal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(Long.parseLong(username));
        if(user.isEmpty()){
            throw new UsernameNotFoundException("user not found");
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.get().getAccountNumber().toString())
                .password("{noop}"+user.get().getPassword())
                .roles(user.get().getUserType().toString().toUpperCase())
                .build();
    }

}
