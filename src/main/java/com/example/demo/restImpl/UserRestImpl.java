package com.example.demo.restImpl;

import com.example.demo.rest.UserRest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class UserRestImpl implements UserRest {


    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        return null;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        return null;
    }
}
