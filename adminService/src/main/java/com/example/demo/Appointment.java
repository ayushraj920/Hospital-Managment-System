package com.example.demo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Time;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appId;

    private String appTime;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = true) // Allowing null values for foreign key
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = true) // Allowing null values for foreign key
    private Doctor doctor;


//    private String prescription;

    private String problem;


    @Enumerated(EnumType.STRING)
    private Status status;

    //    @Enumerated(EnumType.STRING)
//    @Enumerated(EnumType.ORDINAL)
//    private Priority priority;


}