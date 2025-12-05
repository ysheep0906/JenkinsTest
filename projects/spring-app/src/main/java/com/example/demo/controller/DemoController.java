package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Employee;
import com.example.demo.service.EmployeeService;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin()
class EmployeeController {   
	@Autowired
    private EmployeeService employeeService;
	
    @GetMapping("/employees")
    public Employee getAll() {
        return employeeService.getAll();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.add(employee);
    }
}
