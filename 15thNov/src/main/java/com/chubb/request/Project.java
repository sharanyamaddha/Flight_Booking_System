package com.chubb.request;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;

@Entity
public class Project {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long projectId;
	private String projectName;
	
	@JsonIgnore
	@ManyToMany(mappedBy="projectSet")
	private Set<Employee> employeeSet=new HashSet<>();

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Set<Employee> getEmployeeSet() {
		return employeeSet;
	}

	public void setEmployeeSet(Set<Employee> employeeSet) {
		this.employeeSet = employeeSet;
	}
}

