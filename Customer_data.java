package com.abhi.SpringSecEx.Model;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


public class Customer_data {


    private int id;
    private  String name;
    private float ratings;





    //parameterside constructor
    public Customer_data(int id, String name, float ratings) {
        this.id = id;
        this.name = name;
        this.ratings = ratings;
    }


    public Customer_data() {
    }

    //getters and settere
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   public float getRatings(){
        return ratings;
   }

   public void setRatings(float ratings){
        this.ratings=ratings;
   }

   //so u can see with your eyes
    @Override
    public String toString() {
        return "Customer_data{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ratings=" + ratings +
                '}';
    }
}
