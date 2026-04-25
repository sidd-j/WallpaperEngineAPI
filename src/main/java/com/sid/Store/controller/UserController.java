package com.sid.Store.controller;

import com.sid.Store.Service.NasaService;
import com.sid.Store.Service.UserService;
import com.sid.Store.dto.NasaDto;
import com.sid.Store.dto.UserData;
import com.sid.Store.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final NasaService nasaService;
    private final UsersRepository repository;
    public UserController(NasaService nasaService, UsersRepository repository) {
        this.nasaService = nasaService;
        this.repository = repository;
    }

    @GetMapping("/api/apod")
    public NasaDto getApod() {
        return nasaService.getTodayApod();
    }

    @GetMapping("/getUsers")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(repository.findAll());
    }


    @GetMapping("/getByEmail")
    public ResponseEntity<?> getByEmail(@RequestParam String email) {
        return repository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Autowired
    private UserService userService;


    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody UserData data) {

        if (data.getEmail() == null || data.getEmail().isBlank() ||
                data.getPassword() == null || data.getPassword().isBlank() ||
                data.getName() == null || data.getName().isBlank()) {

            return ResponseEntity.badRequest().body("Invalid input data");
        }

        return ResponseEntity.status(201).body(userService.registerUser(data));    }

    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody UserData data) {

        if (data.getEmail() == null || data.getEmail().isBlank() ||
                data.getPassword() == null || data.getPassword().isBlank() )   {

            return ResponseEntity.badRequest().body("Invalid input data");
        }


        return ResponseEntity.status(200).body(userService.loginUser(data));
    }


}