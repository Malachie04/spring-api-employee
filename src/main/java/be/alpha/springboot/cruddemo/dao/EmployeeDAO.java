package be.alpha.springboot.cruddemo.dao;

import be.alpha.springboot.cruddemo.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee>findAll();

    Employee findByID(int theID);

    Employee save(Employee theEmployee);

    void deleteByID(int theID);
}
