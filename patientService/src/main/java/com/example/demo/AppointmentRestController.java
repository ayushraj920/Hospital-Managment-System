package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class AppointmentRestController {

    @Autowired
    AppointmentRepo appRepo;


    @GetMapping("/viewAppointmentByDoctor")
    public List<Appointment> viewAppointmentByDoctor(@RequestParam int id) {

        List<Appointment> appointments = appRepo.findByDoctorId(id);
        return appointments;
    }

    @GetMapping("/viewAllAppointment")
    public List<Appointment> viewAllAppointment() {

        List<Appointment> appointments = appRepo.findAll();
        return appointments;
    }

}
