package firstProject.dataConectivity.patient;


import firstProject.dataConectivity.LoginUser;
import firstProject.dataConectivity.doctor.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


//@RestController
@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    PatientRepo patientRepo;

    @GetMapping("/openRegisterationForm")
    public String openRegisterationForm(Model model){

        Patient patient=new Patient();
        model.addAttribute("patient",patient);
        return "openRegisterationFormPatient";
    }

    @PostMapping("/register")
    public String registerPatient(@ModelAttribute("patient") Patient patient){

         patientRepo.save(patient);
        return "redirect:/patient/viewAllPatients";
    }



    @GetMapping("/openLoginForm")
    public String openLoginForm(Model model){

        LoginUser loginUser=new LoginUser();
        model.addAttribute("loginUser",loginUser);
        return "openLoginFormPatient";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginUser") LoginUser loginUser){

        List<Patient> oldPatients = patientRepo.findByEmail(loginUser.getEmail());

        System.out.println(loginUser.getEmail());
        if(!oldPatients.isEmpty())
        {
            Patient patient = patientRepo.findByEmail(loginUser.getEmail()).get(0);
            if (patient != null) {

                if (Objects.equals(loginUser.getPassword(), patient.getPassword()))
                {
                    return "redirect:http://localhost:7070/patient/openAppointmentForm?id=" + patient.getId();
                }
            }
        }


        return "redirect:/patient/openLoginForm";
    }

    @GetMapping("/login/{email}/{password}")
    public Patient loginUser(@PathVariable String  email, @PathVariable String password) {

        List<Patient> oldPatients = patientRepo.findByEmail(email);
        if(!oldPatients.isEmpty())
        {
            Patient patient = patientRepo.findByEmail(email).get(0);
            if (patient != null) {

                if (Objects.equals(password, patient.getPassword())) {
                    return patient;
                }
            }
        }
        return null;
    }

    @GetMapping("/viewAllPatients")
    public String viewAllPatients(Model model) {

        List<Patient> patients = patientRepo.findAll();
        model.addAttribute("patients", patients);
          return "viewAllPatients";
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<Patient> viewPatient(@PathVariable int id) {

        List<Patient> PatientsList = patientRepo.findById(id);

        if (!PatientsList.isEmpty()) {
            // Return the doctor object with HTTP status 200 OK
            return ResponseEntity.ok(PatientsList.get(0));
        } else {
            // Return HTTP status 404 Not Found if doctor is not found
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/openUpdateForm")
    public String openUpdateForm(Model model,@RequestParam int id){

        List<Patient> patientList=patientRepo.findById(id);
        Patient patient=patientList.get(0);
        model.addAttribute("patient",patient);
        model.addAttribute("id",id);
        return "openUpdateForm";
    }
}
