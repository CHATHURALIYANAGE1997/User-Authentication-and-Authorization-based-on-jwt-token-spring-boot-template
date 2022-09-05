package com.example.demo.serviceImpl;


import com.example.demo.JWT.CustomerUserDetailsService;
import com.example.demo.JWT.JwtUtil;
import com.example.demo.constents.UserConstents;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.UserService;
import com.example.demo.utils.UserUntils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup{}",requestMap);
        try {
            User user = userRepo.findByEmail(requestMap.get("email"));
            if (Objects.isNull(user)) {
                //user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepo.save(getUserFromMap(requestMap));
                return UserUntils.getResponseEntity("SuccessFully Registrered", HttpStatus.OK);
            } else {
                return UserUntils.getResponseEntity("Email is already used", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return UserUntils.getResponseEntity(UserConstents.SOMETHIMG_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private User getUserFromMap(Map<String,String> requestMap){
        User user=new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(passwordEncoder.encode(requestMap.get("password")));
        //user.setPassword(requestMap.get("password"));
        user.setStatus(requestMap.get("status"));
        user.setRole(requestMap.get("role"));
        return user;

    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try {
            JSONObject jsonObject = new JSONObject();
            Authentication auth= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password")));
            if(auth.isAuthenticated()){
                if(customerUserDetailsService.getUserDetails().getStatus().equalsIgnoreCase("ture")){
                    jsonObject.put("token", jwtUtil.generateToken(customerUserDetailsService.getUserDetails().getEmail(), customerUserDetailsService.getUserDetails().getRole()));
                    jsonObject.put("name", customerUserDetailsService.getUserDetails().getEmail());
                    jsonObject.put("role", customerUserDetailsService.getUserDetails().getRole());
                    return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<String>("{\"message\":\""+"Your account is temporarly suspennded,So wait for admin aprove."+"\"}",HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception ex){
            log.error("{}",ex);
        }
        return new ResponseEntity<String>("{\"message\":\""+"Bad Credentials."+"\"}",HttpStatus.BAD_REQUEST);
    }
}
