package pet.store.dao;

 
import pet.store.entity.Employee;
 
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDao extends JpaRepository<Employee, Long>{

}
