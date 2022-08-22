package repository;

import model.Employee;
import org.junit.Assert;
import org.junit.Test;

public class EmployeeRepositoryTest {

    @Test
    public void whenSave() {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        Employee employee = new Employee("Nikita", "Russia");
        employeeRepository.save(employee);
        Employee result = employeeRepository.findById(employee.getId());
        Assert.assertNotNull(result);
    }

}