package com.chubb.request;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long empID;
	private String empName;
	private int empAge;
	
	
	//@OneToOne(cascade=CascadeType.ALL)
//	@OneToMany(cascade=CascadeType.ALL)
//	@JoinColumn(name="fk_emp_id",referencedColumnName="empID")
//	private List<Address> address;
//	
	
	@ManyToMany
	@JoinTable(name="employee_project",
	joinColumns=@JoinColumn(name="employee_id"),
	inverseJoinColumns=@JoinColumn(name="project_id"))
	private Set<Project> projectSet=new HashSet<>();
	
	
	public Set<Project> getProjectSet() {
		return projectSet;
	}
	public void setProjectSet(Set<Project> projectSet) {
		this.projectSet = projectSet;
	}
	public Long getEmpID() {
		return empID;
	}
	public void setEmpID(Long empID) {
		this.empID = empID;
	}
	public String getEmpName() {
		return empName;
	}
//	public List<Address> getAddress() {
//	    return address;
//	}
//
//	public void setAddress(List<Address> address) {
//	    this.address = address;
//	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public int getEmpAge() {
		return empAge;
	}
	public void setEmpAge(int empAge) {
		this.empAge = empAge;
	}
	
	
}
