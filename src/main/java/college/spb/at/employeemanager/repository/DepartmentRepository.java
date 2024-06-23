package college.spb.at.employeemanager.repository;

import college.spb.at.employeemanager.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}