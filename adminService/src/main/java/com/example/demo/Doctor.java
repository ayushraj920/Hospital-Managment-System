package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int doctor_id;
    public String name;
    public String address;
    public String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String password;
    public String gender;
    public String specialization;
    public String experience;
    public String age;
    public String phone;

}