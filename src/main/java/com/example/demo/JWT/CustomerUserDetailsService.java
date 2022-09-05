package com.example.demo.JWT;


import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import com.example.demo.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;


@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    private User userDetails;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername {}",username);
        userDetails=userRepo.findByEmail(username);
        if(!Objects.isNull(userDetails)){
            return new org.springframework.security.core.userdetails.User(userDetails.getEmail(),userDetails.getPassword(),new ArrayList<>());
        }
        else {
            throw new UsernameNotFoundException("User Not Found");
        }
    }

    public User getUserDetails(){
        return userDetails;
    }
}

