package com.example.doctorService;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DoctorAvailbility")
public class DoctorAvailbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int avaibilityId;

    public String startTime;
    public String endTime;
//  public String specialisation;
    public int slots;

    @OneToOne //(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id")
    public Doctor doctor;

    public LocalTime getStartTimeAsLocalTime() {
        String timeWithSeconds = this.startTime + ":00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(timeWithSeconds, formatter);
    }

    public LocalTime getEndTimeAsLocalTime() {
        String timeWithSeconds = this.endTime + ":00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return  LocalTime.parse(timeWithSeconds, formatter);
    }

}
