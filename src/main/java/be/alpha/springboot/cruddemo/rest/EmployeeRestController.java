package be.alpha.springboot.cruddemo.rest;

import be.alpha.springboot.cruddemo.entity.Employee;
import be.alpha.springboot.cruddemo.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    //quick and dirty : inject employee
    public EmployeeRestController(EmployeeService theemployeeService){
        this.employeeService = theemployeeService;
    }

    //expose the endPoint for all employees
    @GetMapping("/employees")
    public List<Employee>findAll(){
        return employeeService.findAll();
    }

    //get single employee by ID
    @GetMapping("/employees/{employeeID}")
    public Employee getEmployee(@PathVariable int employeeID){
        //get the employee by id
        Employee theEmployee = employeeService.findByID(employeeID);


        if (theEmployee == null) {
            throw  new RuntimeException("Employee id not found - " + employeeID);
        }

        //return the found employee
        return  theEmployee;
    }

    //adding mapping for POST/employee - add new employee
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee){
        //also just in case they pass an id in json... set id to O
        // this is to force a save of new item... instead of update
        theEmployee.setId(0);

        Employee tbEmployee = employeeService.save(theEmployee);

        return tbEmployee;
    }

    //updating an existing employee
    @PutMapping("/employees")
    public Employee updatingEMployee(@RequestBody Employee theEmployee){

        Employee tbEmployee = employeeService.save(theEmployee);

        return tbEmployee;
    }

}
