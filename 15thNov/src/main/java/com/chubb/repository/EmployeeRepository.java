package com.chubb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chubb.request.Employee;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

	List<Employee> findAllByEmpID(Long empID);
}
