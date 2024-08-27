package com.example.doctorService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntity {
    DoctorAvailbility doctorAvailbility;
    String s;

//    Doctor doctor;
}
