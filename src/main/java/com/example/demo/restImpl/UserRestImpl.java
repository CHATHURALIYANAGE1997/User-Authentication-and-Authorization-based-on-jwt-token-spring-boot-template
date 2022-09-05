package com.example.demo.restImpl;

import com.example.demo.constents.UserConstents;
import com.example.demo.rest.UserRest;
import com.example.demo.service.UserService;
import com.example.demo.utils.UserUntils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class UserRestImpl implements UserRest {
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {

            return userService.signUp(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return UserUntils.getResponseEntity(UserConstents.SOMETHIMG_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {

            return userService.login(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return UserUntils.getResponseEntity(UserConstents.SOMETHIMG_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

