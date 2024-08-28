package firstProject.dataConectivity.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer> {

    public List<Patient> findByEmail(String email);
    public List<Patient> findById(int id);
}
