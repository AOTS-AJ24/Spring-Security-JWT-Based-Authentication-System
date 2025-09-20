package com.abhi.SpringSecEx.Controller;


import com.abhi.SpringSecEx.Model.LoginData;
import com.abhi.SpringSecEx.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService serv;

    @PostMapping("/register")
    public LoginData register(@RequestBody LoginData user){
        return serv.register(user);
    }

    @PostMapping("/login")
    public String Login(@RequestBody LoginData user){
        System.out.println(user);
        return serv.verify(user);
    }




}

