package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Employee;

@Service
public class EmployeeService {
    Employee emp = new Employee();
    
    public Employee add(Employee employee) {
    	emp.setId(1);
    	emp.setFirstName("test1");
    	emp.setLastName("test2");
    	emp.setEmailAddress("test@test.com");
   
        return emp;
    }

    public Employee getAll() {
        return emp;
    }
}
