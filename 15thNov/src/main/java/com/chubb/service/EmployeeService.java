package com.chubb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chubb.repository.EmployeeRepository;
import com.chubb.repository.ProjectRepository;
import com.chubb.request.Employee;
import com.chubb.request.Project;

import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeService {
	
	@Autowired
	EmployeeRepository empRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	public List<Employee> getEmployeeDetails(Long empID){
		if(null!=empID) {
			return empRepository.findAllByEmpID(empID);
		}
		else {
			return empRepository.findAll();
		}
	}
	public Employee insertEmployee(Employee employee) {
		
		log.debug(employee.toString());
		return empRepository.save(employee);
	}
	

    public Employee assignProjectToEmployee(Long empID, Long projectId) {
        Set<Project> projectSet = null;
        Employee employee = empRepository.findById(empID).get();
        Project project = projectRepository.findById(projectId).get();
        projectSet =  employee.getProjectSet();
        projectSet.add(project);
        employee.setProjectSet(projectSet);
        return empRepository.save(employee);
    }
	

}
