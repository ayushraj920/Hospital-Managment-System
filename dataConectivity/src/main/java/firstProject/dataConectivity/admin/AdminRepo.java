package firstProject.dataConectivity.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Integer> {

    public List<Admin> findByEmail(String email);
}
