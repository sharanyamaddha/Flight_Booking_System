package com.chubb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chubb.repository.EmployeeRepository;
import com.chubb.repository.ProjectRepository;
import com.chubb.request.Employee;
import com.chubb.request.Project;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProjectService {

	@Autowired
	ProjectRepository projectRepository;
	
	public List<Project> getProjectDetails(Long projectId){
		if(null!=projectId) {
			return projectRepository.findAllByProjectId(projectId);
		}
		else {
			return projectRepository.findAll();
		}
	}
	public Project insertProject(Project project) {
		
		//log.debug(employee.toString());
		return projectRepository.save(project);
	}
}
