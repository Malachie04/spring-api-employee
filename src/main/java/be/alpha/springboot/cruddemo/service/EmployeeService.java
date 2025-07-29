package be.alpha.springboot.cruddemo.service;

import be.alpha.springboot.cruddemo.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee>findAll();

    Employee findByID(int theID);

    Employee save(Employee theEmployee);

    void deleteByID(int theID);
}
