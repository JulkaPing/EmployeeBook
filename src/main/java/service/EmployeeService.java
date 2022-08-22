package service;

import model.Employee;
import repository.EmployeeRepository;

public class EmployeeService {

    private EmployeeRepository employeeRepository = new EmployeeRepository();

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public boolean delete(long id) {
        return employeeRepository.delete(id);
    }

    public Employee[] findAll() {
        return employeeRepository.findAll();
    }


}
