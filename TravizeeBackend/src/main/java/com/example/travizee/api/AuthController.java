package com.example.travizee.api;

import com.example.travizee.dao.LoginRepository;
import com.example.travizee.model.UserModel;
import com.example.travizee.payload.ApiResponse;
import com.example.travizee.payload.SignUpRequest;
import com.example.travizee.payload.SignupResponse;
import com.example.travizee.security.JwtTokenProvider;
import com.example.travizee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    LoginRepository loginRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;



    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {

        if(loginRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(loginRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        UserModel user = new UserModel(signUpRequest.getEmail().substring(0,signUpRequest.getEmail().indexOf('@')),signUpRequest.getEmail(), signUpRequest.getPassword());
        String token = tokenProvider.generateToken(signUpRequest.getEmail());
        user.setToken(token);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserModel result = loginRepository.save(user);


//        ConfirmationToken confirmationToken = new ConfirmationToken(user, token, user.getId());
//
//        confirmationTokenRepository.save(confirmationToken);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getEmail()).toUri();

        return ResponseEntity.created(location).body(new SignupResponse(true, "User registered Successfully!", token,user.getId().toString() ));
    }


    @GetMapping("/login_error")
    public ResponseEntity<?> handleFailedLogin(@RequestParam("email")String email){
        if (!loginRepository.existsByEmail(email)){
            return new ResponseEntity(new ApiResponse(false, "User not found, Please register."), HttpStatus.UNAUTHORIZED);

        }
//        UserModel userModel = loginRepository.findByEmail(email).orElseThrow();

        return new ResponseEntity(new ApiResponse(false, "Please check your email/password or try again later."), HttpStatus.BAD_REQUEST);

    }
}
