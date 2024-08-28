package firstProject.dataConectivity.doctor;

import firstProject.dataConectivity.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorRepo doctorRepo;

    @GetMapping("/openRegisterationForm")
    public String openRegisterationForm(Model model) {

        Doctor doctor = new Doctor();
        model.addAttribute("doctor", doctor);
        return "openRegisterationFormDoctor";
    }

    @PostMapping("/register")
    public String registerDoctor(@ModelAttribute("doctor") Doctor doctor) {

        doctorRepo.save(doctor);
        return "redirect:/doctor/viewAllDoctors";
    }


    @GetMapping("/openLoginForm")
    public String openLoginForm(Model model) {

        LoginUser loginUser = new LoginUser();
        model.addAttribute("loginUser", loginUser);
        return "openLoginFormDoctor";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginUser") LoginUser loginUser) {

        List<Doctor> oldDoctors = doctorRepo.findByEmail(loginUser.getEmail());

        System.out.println(loginUser.getEmail());
        if (!oldDoctors.isEmpty()) {
            Doctor doctor = doctorRepo.findByEmail(loginUser.getEmail()).get(0);
            if (doctor != null) {

                if (Objects.equals(loginUser.getPassword(), doctor.getPassword())) {
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

        Optional<Doctor> optionalDoctor = doctorRepo.findById(doctorId);

        if (optionalDoctor.isPresent()) {

            return ResponseEntity.ok(optionalDoctor.get());
        } else {

            return ResponseEntity.notFound().build();
        }
    }

}


