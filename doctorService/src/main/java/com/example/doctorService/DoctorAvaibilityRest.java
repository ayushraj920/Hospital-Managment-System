package com.example.doctorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorAvaibilityRest
{
    @Autowired
    DoctorAvaibilityRepo daRepo;

    @PostMapping("/bookTiming")
    public Appointment bookTiming(@RequestBody Appointment appointment)
    {
        List<DoctorAvailbility> availbilities = daRepo.findBySlotsGreaterThan(0);

        if (availbilities.size() == 0)
        {
            return null;
        }

        int size = availbilities.size();
        int idx = (int)(Math.random() *(size));

        DoctorAvailbility da = availbilities.get(idx);

        LocalTime startTime = da.getStartTimeAsLocalTime();
        LocalTime endTime = da.getEndTimeAsLocalTime();

        System.out.println(startTime);
        System.out.println(endTime);

        String sTime = da.startTime;

        System.out.println(sTime);

        long totalTimeAvailable = Duration.between(startTime, endTime).toMinutes();

        int timeMinutes = (int) totalTimeAvailable;
        int timePerSlots = timeMinutes / da.slots;
        startTime = startTime.plusMinutes(timePerSlots);
        Time newStartTime = Time.valueOf(startTime);

        appointment.setAppTime(sTime);
        System.out.println(appointment.getAppTime());

        da.startTime = String.valueOf(startTime);
        da.slots--;
        appointment.setDoctor(da.getDoctor());
        appointment.setStatus(Status.SCHEDULED);

        daRepo.save(da);

        return appointment;
    }
}