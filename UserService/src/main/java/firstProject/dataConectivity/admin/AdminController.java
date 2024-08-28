package firstProject.dataConectivity.admin;

import firstProject.dataConectivity.Appointment;
import firstProject.dataConectivity.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminRepo adminRepo;


    @GetMapping("/openLoginForm")
    public String openLoginForm(Model model) {

        LoginUser loginUser = new LoginUser();


        model.addAttribute("loginUser", loginUser);
        return "openLoginFormAdmin";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute("loginUser") LoginUser loginUser) {

        List<Admin> oldAdmins = adminRepo.findByEmail(loginUser.getEmail());

        System.out.println(loginUser.getEmail());

        if (!oldAdmins.isEmpty()) {
            Admin admin = adminRepo.findByEmail(loginUser.getEmail()).get(0);
            if (admin != null) {

                if (Objects.equals(loginUser.getPassword(), admin.getPassword())) {
                    return "adminHome";
                }
            }
        }

        return "redirect:/admin/openLoginForm";
    }


    @GetMapping("/viewAllAppointment")
    public String viewAppointmentByDoctor(Model model) {


        String url = "http://localhost:7070/patient/viewAllAppointment";

        RestTemplate restTemplate = new RestTemplate();
        List<Appointment> appointments = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Appointment>>() {
                }
        ).getBody();

        model.addAttribute("appointments", appointments);
        return "viewAllAppointment";

    }

}
