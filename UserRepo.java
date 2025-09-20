package com.abhi.SpringSecEx.Repo;


import com.abhi.SpringSecEx.Model.Customer_data;
import com.abhi.SpringSecEx.Model.LoginData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository <LoginData,Integer>{

    LoginData findByUsername(String username);


}
