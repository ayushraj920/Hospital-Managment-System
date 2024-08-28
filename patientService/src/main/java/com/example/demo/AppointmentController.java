package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/patient")
public class AppointmentController {

    @Autowired
    AppointmentRepo appRepo;


    @PostMapping("/bookAppointment")
    public String bookAppointment(@ModelAttribute("appointment") Appointment appointment, @RequestParam int id) {


        RestTemplate restTemplate = new RestTemplate();


        Patient patient = restTemplate.getForObject("http://localhost:9090/patient/info/" + id, Patient.class);

        appointment.setPatient(patient);
        appointment.setProblem(appointment.getProblem());


        appointment.setStatus(Status.PENDING);
        appointment.setDoctor(null);

        Appointment saveapp = restTemplate.postForObject("http://localhost:4040/admin/appointmentFixer", appointment, Appointment.class);
        appointment.setAppTime(saveapp.getAppTime());
        saveapp.setProblem(appointment.getProblem());
        appRepo.save(saveapp);

        return "home";
    }


    @GetMapping("/openAppointmentForm")
    public String openAppointmentForm(@RequestParam int id, Model model) {


        Appointment appointment = new Appointment();

        RestTemplate restTemplate = new RestTemplate();
        Patient patient = restTemplate.getForObject("http://localhost:9090/patient/info/" + id, Patient.class);

        appointment.setPatient(patient);

        model.addAttribute("appointment", appointment);
        model.addAttribute("id", id);

        return "openAppointmentForm";
    }


    @GetMapping("/viewAppointment")
    public String viewAppointment(Model model, @RequestParam int id) {


        RestTemplate restTemplate = new RestTemplate();

        List<Appointment> appointments = appRepo.findByPatientId(id);
        if (!appointments.isEmpty()) {

            model.addAttribute("appointment", appointments);
            model.addAttribute("id", id);
        }

        return "viewAppointment";
    }

}
