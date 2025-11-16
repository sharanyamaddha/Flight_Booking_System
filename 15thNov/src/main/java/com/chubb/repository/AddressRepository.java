package com.chubb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chubb.request.Address;

public interface AddressRepository extends JpaRepository<Address,Long>{

}
