package actions.impl;

import input.ConsoleInput;
import input.Input;
import mapper.EmployeeMapper;
import model.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import repository.impl.EmployeeMemRepository;
import service.EmployeeService;

import java.util.List;

public class FindAllActionTest {
    private final FindAllAction findAllAction = new FindAllAction();
    private final EmployeeMemRepository employeeMemRepository = Mockito.mock(EmployeeMemRepository.class);
    private final EmployeeMapper employeeMapper = new EmployeeMapper();

    private final EmployeeService employeeService = new EmployeeService(employeeMemRepository, employeeMapper);

    @Test
    public void whenFindAll() {
        Input input = Mockito.mock(ConsoleInput.class);
        List<Employee> employees = employeeMemRepository.findAll();
        boolean findByNameSuccess = findAllAction.execute(input, employeeService);
        Assert.assertTrue(findByNameSuccess);
    }

}