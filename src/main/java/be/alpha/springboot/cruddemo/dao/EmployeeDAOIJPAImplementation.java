package be.alpha.springboot.cruddemo.dao;

import be.alpha.springboot.cruddemo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOIJPAImplementation implements EmployeeDAO{
    //define field for entityManager
    private final EntityManager entityManager;

    //Set up constructor injection

    @Autowired
    public EmployeeDAOIJPAImplementation(EntityManager theEntityManager){
        this.entityManager = theEntityManager;
    }

    @Override
    public List<Employee> findAll() {
        //Create a query
        TypedQuery<Employee> theQuery = entityManager.createQuery("FROM Employee", Employee.class);
        //Execute a query
        List<Employee> employees = theQuery.getResultList();
        //Return the result
        return employees;
    }

    @Override
    public Employee findByID(int theID) {
        //get and directly return the employee found based on its ID

        Employee theEmployee=entityManager.find(Employee.class, theID);

        //return the employee found
        return  theEmployee;
    }

    @Override
    public Employee save(Employee theEmployee) {
        //Save employee
        Employee dbEmployee = entityManager.merge(theEmployee);
        //return the employee
        return dbEmployee;
    }

    @Override
    public void deleteByID(int theID) {
        //find employee by id
        Employee theEmployee = entityManager.find(Employee.class, theID);
        //remove the employee
        entityManager.remove(theEmployee);
    }
}
