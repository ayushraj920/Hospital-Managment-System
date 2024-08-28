package com.example.doctorService;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/doctor")
public class DoctorAvailabilityController {

    @Autowired
    DoctorAvaibilityRepo daRepo;
    AppointmentRepo appRepo;

    @GetMapping("/openRegisterAvailabilityForm")
    public String openLoginForm(Model model, @RequestParam("doctorId") int doctorId) {

        DoctorAvailbility doctorAvailbility = new DoctorAvailbility();

        RestTemplate restTemplate = new RestTemplate();
        Doctor doctor = restTemplate.getForObject("http://localhost:9090/doctor/info/" + doctorId, Doctor.class);

        model.addAttribute("doctorAvailbility", doctorAvailbility);
        model.addAttribute("id", doctorId);
        doctorAvailbility.setDoctor(doctor);
        System.out.println(doctorAvailbility);
        return "openRegisterAvailabilityForm";
    }

    @PostMapping("/registerAvailability")
    private String registerAvailability(@ModelAttribute("doctorAvailbility") DoctorAvailbility doctorAvailbility, @RequestParam int id) {


        RestTemplate restTemplate = new RestTemplate();
        Doctor doctor = restTemplate.getForObject("http://localhost:9090/doctor/info/" + id, Doctor.class);

        doctorAvailbility.setDoctor(doctor);

        DoctorAvailbility savedAvailability = daRepo.save(doctorAvailbility);
        System.out.println(savedAvailability);
        return "success";
    }

    @GetMapping("/viewAppointmentByDoctor")
    public String viewAppointmentByDoctor(Model model, @RequestParam("id") int id) {


        String url = "http://localhost:7070/patient/viewAppointmentByDoctor?id=" + id;

        RestTemplate restTemplate = new RestTemplate();

        List<Appointment> appointments = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Appointment>>() {
                }
        ).getBody();

        model.addAttribute("appointments", appointments);
        model.addAttribute("id", id);


        return "viewAppointmentByDoctor";
    }


}
