package com.chubb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chubb.request.Employee;
import com.chubb.request.Project;

public interface ProjectRepository extends JpaRepository<Project ,Long>{

	List<Project> findAllByProjectId(Long projectId);
}

