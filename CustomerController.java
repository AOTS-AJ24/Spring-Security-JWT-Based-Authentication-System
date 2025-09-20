package com.abhi.SpringSecEx.Controller;

import com.abhi.SpringSecEx.Model.Customer_data;
import com.abhi.SpringSecEx.Model.LoginData;
import com.abhi.SpringSecEx.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {

    private List<Customer_data> get_D = new ArrayList<>(List.of(
            new Customer_data(1,"aj",3.3f),
            new Customer_data(2,"daya",4.56f),
            new Customer_data(3,"acp",5)
    ));



    @GetMapping("/customer")
    public List<Customer_data>getdata(){
        return get_D;
    }


    @PostMapping("/customer")
    public Customer_data add_data(@RequestBody Customer_data c_d){
        get_D.add(c_d);
        System.out.println(get_D);
        return c_d ;
    }

    //now get all login details

    @Autowired
    private UserRepo repo;

    @GetMapping("/logininfo")
    public List<LoginData> getlogindata(){
        return  repo.findAll();   //now can see all those who have info in login db
    }





}
