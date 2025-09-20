package com.abhi.SpringSecEx.Services;

import com.abhi.SpringSecEx.Model.LoginData;
import com.abhi.SpringSecEx.Repo.UserRepo;
import com.mysql.cj.protocol.x.XAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    AuthenticationManager auManager;

    @Autowired
    JWTService jwtServ;


    //to  bcrypt we have an inbuilt library
    private BCryptPasswordEncoder encode_it =new BCryptPasswordEncoder(12);

    public LoginData register (LoginData user){

        System.out.println("the name of user is : "+ user.getUsername() +"\nRaw password before hashing: " + user.getPassword());
        user.setPassword(encode_it.encode(user.getPassword()));
        return repo.save(user);

    }


    public String verify(LoginData user) {
        Authentication auth =
                auManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        if (auth.isAuthenticated()) return "*** Login success ***\n\n the generated token is : " + jwtServ.generateToken(user.getUsername());
        else  return "*** Login failed ***  \n  +++ Try again! +++ ";

    }
}
