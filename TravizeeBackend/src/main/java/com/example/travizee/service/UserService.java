package com.example.travizee.service;


import com.example.travizee.dao.LoginRepository;
import com.example.travizee.model.UserModel;
import com.example.travizee.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private LoginRepository loginRepository;


//    @Autowired
//    public UserService(LoginRepository loginRepository) {
//        this.loginRepository = loginRepository;
//    }

    public UserModel addUser(UserModel userModel){
        return loginRepository.save(userModel);

    }

    public Iterable<UserModel> findAllUsers(){
        return loginRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserModel user = loginRepository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + username)
                );

        return UserPrincipal.create(user);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        UserModel user = loginRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user);
    }






    public void updateUserDetails(UserModel user){
        loginRepository.save(user);
    }

    public UserModel getUserById(Long id){
        return loginRepository.findById(id).orElseThrow();
    }




    @GetMapping("/getUserId")
    public Long getUserId(String sub) {
      UserModel userModel =  loginRepository.findByEmail(sub).orElseThrow();
        return userModel.getId();
    }



}
