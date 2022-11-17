package actions.impl;

import input.ConsoleInput;
import input.Input;
import mapper.EmployeeMapper;
import model.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import repository.impl.EmployeeMemRepository;
import service.EmployeeService;

import java.util.Collections;
import java.util.List;

public class FindByNameActionTest {
    private final FindByNameAction findByNameAction = new FindByNameAction();
    private final EmployeeMemRepository employeeMemRepository = Mockito.mock(EmployeeMemRepository.class);
    private final EmployeeMapper employeeMapper = new EmployeeMapper();
    private final EmployeeService employeeService = new EmployeeService(employeeMemRepository, employeeMapper);


    @Test
    public void whenFindByName() {
        Input input = Mockito.mock(ConsoleInput.class);
        Employee employee = new Employee();
        List mockList = Collections.singletonList(Mockito.mock(EmployeeMemRepository.class));

        Mockito.when(employeeMemRepository.findByName(ArgumentMatchers.anyString())).thenReturn(List.of());

        boolean findByNameSuccess = findByNameAction.execute(input, employeeService);
        Assert.assertTrue(findByNameSuccess);

    }
}