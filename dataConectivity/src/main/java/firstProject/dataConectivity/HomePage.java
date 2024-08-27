package firstProject.dataConectivity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePage {

    @GetMapping("/home")
    public String goToHome()
    {
        return  "home";
    }

    @GetMapping("/patientHome")
    public String goToPatientHome()
    {
        return  "patientHome";
    }
}
