package repository;

import model.Employee;
import org.junit.Assert;
import org.junit.Test;
import repository.impl.EmployeeMemRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class EmployeeMemRepositoryTest {

    private final EmployeeMemRepository employeeMemRepository = new EmployeeMemRepository();
    @Test
    public void saveTest() {
        Employee employee = employeeMemRepository.save(new Employee("Nikita", "Moscow"));
        Assert.assertTrue(employeeMemRepository.findById(employee.getId()).isPresent());
    }

    @Test
    public void updateTest() {
        Employee employee = employeeMemRepository.save(new Employee("Nikita", "Moscow"));
        boolean result = employeeMemRepository.update(employee.getId(), new Employee("Nikita", "Saratov"));
        Assert.assertTrue(result);
        Employee checkEmployeeUpdated = employeeMemRepository.findById(employee.getId()).orElseThrow();
        Assert.assertEquals("Nikita", checkEmployeeUpdated.getName());
        Assert.assertEquals("Saratov", checkEmployeeUpdated.getCity());
    }

    @Test
    public void updateNoteSuccessTest() {
        employeeMemRepository.save(new Employee("Nikita", "Moscow"));
        boolean result = employeeMemRepository.update(1L, new Employee("Nikita", "Saratov"));
        Assert.assertFalse(result);
    }

    @Test
    public void deleteTest() {
        Employee employeeFirst = employeeMemRepository.save(new Employee("Nikita", "Moscow"));
        Employee employeeSecond = employeeMemRepository.save(new Employee("Diana", "Samara"));
        boolean result = employeeMemRepository.delete(employeeSecond.getId());
        Assert.assertTrue(result);
        Assert.assertEquals(1, employeeMemRepository.findAll().size());
    }

    @Test
    public void deleteNoteSuccessTest() {
        employeeMemRepository.save(new Employee("Nikita", "Moscow"));
        employeeMemRepository.save(new Employee("Diana", "Samara"));
        boolean result = employeeMemRepository.delete(1L);
        Assert.assertFalse(result);
        Assert.assertEquals(2, employeeMemRepository.findAll().size());
    }

    @Test
    public void findByNameTest() {
        employeeMemRepository.save(new Employee("Nikita", "Moscow"));
        employeeMemRepository.save(new Employee("Diana", "Samara"));
        employeeMemRepository.save(new Employee("Nikita", "Irkutsk"));

        List<Employee> employees = employeeMemRepository.findByName("Nikita");
        Assert.assertEquals(2, employees.size());
        employees.forEach(employee -> Assert.assertEquals("Nikita", employee.getName()));
    }

    @Test
    public void findByNameTestIsEmpty() {
        employeeMemRepository.save(new Employee("Nikita", "Moscow"));
        employeeMemRepository.save(new Employee("Diana", "Samara"));
        employeeMemRepository.save(new Employee("Nikita", "Irkutsk"));

        List<Employee> employees = employeeMemRepository.findByName("Ivan");
        Assert.assertTrue(employees.isEmpty());
    }

    @org.junit.Test
    public void test() {
        System.out.println(LocalDateTime.now());
    }
}