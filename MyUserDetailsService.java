package com.abhi.SpringSecEx.Services;

import com.abhi.SpringSecEx.Model.LoginData;
import com.abhi.SpringSecEx.Model.UserPrincipal;
import com.abhi.SpringSecEx.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {



    @Autowired
    private UserRepo repo ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LoginData user  = repo.findByUsername(username);

        if (user == null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("user not found !");
        }
        return new UserPrincipal(user);
    }
}
