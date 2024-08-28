package com.example.demo;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @PostMapping("/appointmentFixer")
    public Appointment fixAppointment(@RequestBody Appointment appointment) {

        RestTemplate restTemplate = new RestTemplate();
        Appointment saveApp = restTemplate.postForObject("http://localhost:9091/doctor/bookTiming", appointment, Appointment.class);
        return saveApp;

    }


}
