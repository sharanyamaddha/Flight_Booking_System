package com.chubb.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chubb.repository.EmployeeRepository;
import com.chubb.request.Employee;
import com.chubb.service.EmployeeService;


@RestController
public class EmployeeController {

	@Autowired
	EmployeeService service;
	
	@GetMapping("/emp")
	List<Employee> getEmployee(@PathVariable(required=false) Long empID){
		return service.getEmployeeDetails(empID);
	}
//	String getEmployee() {
//		return "hello";
//	}
	@PostMapping("/emp")
	Employee saveAddress(@RequestBody Employee employee) {
		service.insertEmployee(employee);
		return employee;
	}
	
    @PutMapping("/{empId}/project/{projectId}")
    public Employee assignProjectToEmployee(
            @PathVariable Long empId,
            @PathVariable Long projectId
    ){
        return service.assignProjectToEmployee(empId, projectId);
    }
	
}