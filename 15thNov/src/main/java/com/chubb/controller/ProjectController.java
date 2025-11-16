package com.chubb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.chubb.request.Project;
import com.chubb.service.ProjectService;

@RestController
public class ProjectController {

	@Autowired
	ProjectService service;
	
	@GetMapping("/getprojects")
	List<Project> getEmployee(@PathVariable(required=false) Long projectId){
		return service.getProjectDetails(projectId);
	}
//	String getEmployee() {
//		return "hello";
//	}
	@PostMapping("/postproject")
	Project saveAddress(@RequestBody Project project) {
		service.insertProject(project);
		return project;
	}
}
