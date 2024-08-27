package firstProject.dataConectivity.doctor;

import firstProject.dataConectivity.LoginUser;
import firstProject.dataConectivity.doctor.Doctor;
import firstProject.dataConectivity.doctor.DoctorRepo;
import firstProject.dataConectivity.doctor.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorRepo doctorRepo;

    @GetMapping("/openRegisterationForm")
    public String openRegisterationForm(Model model){

        Doctor doctor=new Doctor();
        model.addAttribute("doctor",doctor);
        return "openRegisterationFormDoctor";
    }

    @PostMapping("/register")
    public String registerDoctor(@ModelAttribute("doctor") Doctor doctor){

        doctorRepo.save(doctor);
        return "redirect:/doctor/viewAllDoctors";
    }

//    @PutMapping("/update/{email}/{password}")
//    public Doctor updateDoctor(@PathVariable String email, @PathVariable String password, @RequestBody Doctor newDoctor) {
//
//        List<Doctor> oldDoctors = doctorRepo.findByEmail(email);
//
//        if(!oldDoctors.isEmpty())
//        {
//            Doctor oldDoctor=oldDoctors.get(0);
//
//            if (oldDoctor.getPassword().equals(password)) {
//                if (newDoctor.getName() != null) {
//                    oldDoctor.setName(newDoctor.getName());
//                }
//                if (newDoctor.getAddress() != null) {
//                    oldDoctor.setAddress(newDoctor.getAddress());
//                }
//                if (newDoctor.getPassword() != null) {
//                    oldDoctor.setPassword(newDoctor.getPassword());
//                }
//                if (newDoctor.getBloodGroup() != null) {
//                    oldDoctor.setBloodGroup(newDoctor.getBloodGroup());
//                }
//                if (newDoctor.getAge() != 0) {
//                    oldDoctor.setAge(newDoctor.getAge());
//                }
//                if (newDoctor.getEmail() != null) {
//                    oldDoctor.setEmail(newDoctor.getEmail());
//                }
//
//                return doctorRepo.save(oldDoctor);
//            }
//        }
//        return null;
//    }


    @GetMapping("/openLoginForm")
    public String openLoginForm(Model model){

        LoginUser loginUser=new LoginUser();
        model.addAttribute("loginUser",loginUser);
        return "openLoginFormDoctor";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginUser") LoginUser loginUser){

        List<Doctor> oldDoctors = doctorRepo.findByEmail(loginUser.getEmail());

        System.out.println(loginUser.getEmail());
        if(!oldDoctors.isEmpty())
        {
            Doctor doctor = doctorRepo.findByEmail(loginUser.getEmail()).get(0);
            if (doctor != null) {

                if (Objects.equals(loginUser.getPassword(), doctor.getPassword()))
                {
                    return "redirect:http://localhost:9091/doctor/openRegisterAvailabilityForm?doctorId=" + doctor.getDoctor_id();
                }
            }
        }


        return "redirect:/doctor/openLoginForm";
    }


    @GetMapping("/viewAllDoctors")
    public String viewAllDoctors(Model model) {

        List<Doctor> doctors = doctorRepo.findAll();
        model.addAttribute("doctors", doctors);
        return "viewAllDoctors";
    }

    @GetMapping("/info/{doctorId}")
    public ResponseEntity<Doctor> viewDoctor(@PathVariable int doctorId) {
        // Find the doctor by ID
        Optional<Doctor> optionalDoctor = doctorRepo.findById(doctorId);

        // Check if doctor is present
        if (optionalDoctor.isPresent()) {
            // Return the doctor object with HTTP status 200 OK
            return ResponseEntity.ok(optionalDoctor.get());
        } else {
            // Return HTTP status 404 Not Found if doctor is not found
            return ResponseEntity.notFound().build();
        }
    }

}


