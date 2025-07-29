package be.alpha.springboot.cruddemo.rest;

import be.alpha.springboot.cruddemo.entity.Employee;
import be.alpha.springboot.cruddemo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    private ObjectMapper objectMapper;

    //quick and dirty : inject employee
    public EmployeeRestController(EmployeeService theemployeeService, ObjectMapper objectMapper){
        this.employeeService = theemployeeService;
        this.objectMapper= objectMapper;
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
    public Employee updatingEmployee(@RequestBody Employee theEmployee){
        Employee tbEmployee = employeeService.save(theEmployee);
        return tbEmployee;
    }

    //add mapping for PATCH/employee/id
    @PatchMapping("/employees/{employeeID}")
    public Employee patchEmployee(@PathVariable int employeeID,
                                  @RequestBody Map<String,Object> patchLoad){
        Employee tempEmployee = employeeService.findByID(employeeID);

        //Exception if not employee not found
        if(tempEmployee == null) throw new RuntimeException("Employee not found - "+employeeID);

        //throw error if request body contains exception
        if(patchLoad.containsKey("id")) throw new RuntimeException("Employee id not allowed in request body" + employeeID);

        Employee patchedEmployee = apply(patchLoad, tempEmployee);

        Employee dbEmployee = employeeService.save(patchedEmployee);

        return dbEmployee;
    }

    private Employee apply(Map<String, Object> patchLoad, Employee tempEmployee) {

        //Convert employee to JSON object Node
        ObjectNode employeeNode = objectMapper.convertValue(tempEmployee, ObjectNode.class);

        //Convert the patchPayLoad map to a JSON object node
        ObjectNode patchNode = objectMapper.convertValue(patchLoad, ObjectNode.class);

        //merge the patch updates into rhe employee node
        employeeNode.setAll(patchNode);

        return  objectMapper.convertValue(employeeNode, Employee.class);
    }

    //add mapping for DELETE/employee
    @DeleteMapping("/employees/{employeeID}")
    public String deleteByID(@PathVariable int employeeID){
        Employee tempEmployee = employeeService.findByID(employeeID);
        if(tempEmployee==null) throw new RuntimeException("This employee does not exist - " +employeeID);

        employeeService.deleteByID(employeeID);
        return  "Deleted employee id " + employeeID;
    }


}
