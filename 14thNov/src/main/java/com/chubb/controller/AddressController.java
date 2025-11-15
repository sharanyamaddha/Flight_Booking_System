package com.chubb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.chubb.request.Address;
import com.chubb.repository.AddressRepository;

@RestController
public class AddressController {

    @Autowired
    AddressRepository repo;

    @PostMapping("/address")
    public Address saveAddress(@RequestBody Address address) {
        return repo.save(address);
    }
}
