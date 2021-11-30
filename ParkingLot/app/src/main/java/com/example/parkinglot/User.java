package com.example.parkinglot;

public class User {

    public String first_name, last_name, email, phone_number, cnic, created_at, updated_at, is_deleted;

    public User(){

    }

    public User(String first_name, String last_name, String email, String phone_number, String cnic, String created_at, String updated_at, String is_deleted){
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.cnic = cnic;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.is_deleted = is_deleted;

    }
}
