package com.example.doctorService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface DoctorAvaibilityRepo extends JpaRepository<DoctorAvailbility,Integer> {

    public List<DoctorAvailbility>  findBySlotsGreaterThan(int slots);
    public List<DoctorAvailbility>  findById(int id);
}
