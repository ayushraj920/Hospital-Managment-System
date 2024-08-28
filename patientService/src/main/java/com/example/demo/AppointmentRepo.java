package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {

    public List<Appointment> findByPatientId(int PatientId);

    public List<Appointment> findByPatientEmail(String email);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.doctor_id = :doctorId")
    public List<Appointment> findByDoctorId(@Param("doctorId") int doctorId);


}
