package com.example.demo.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RequestMapping(path="/user")
public interface UserRest {
    @PostMapping(path="/signup")
    public ResponseEntity<String> signUp(@RequestBody(required=true) Map<String, String> requestMap);
    
    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String,String> requestMap);
}
