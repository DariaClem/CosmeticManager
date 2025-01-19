//package com.cosmetic_manager.cosmetic_manager.controller;
//
//import com.cosmetic_manager.cosmetic_manager.dto.LoginDto;
//import com.cosmetic_manager.cosmetic_manager.dto.UserDto;
//import com.cosmetic_manager.cosmetic_manager.model.User;
//import com.cosmetic_manager.cosmetic_manager.service.JwtService;
//import com.cosmetic_manager.cosmetic_manager.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//    @Autowired
//    private JwtService jwtService;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @PostMapping("/register")
//    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
//        System.out.println(userDto);
//
//        userService.createNewUser(userDto);
//        return ResponseEntity.ok("User created successfully!");
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginDto request) {
//        System.out.println(request);
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//            );
//        } catch (BadCredentialsException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//
//        System.out.println("aici" + request.getEmail());
//
//        User user = userService.getUserByEmail(request.getEmail()).orElse(null);
//
//        System.out.println("user" + user);
//
//        String token = jwtService.generateToken(user);
//
//        System.out.println("token" + token);
//
//        return ResponseEntity.ok(token);
//
//    }
//}
