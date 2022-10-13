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

public class DeleteActionTest {
    private final DeleteAction deleteAction = new DeleteAction();
    private final EmployeeMemRepository employeeMemRepository = Mockito.mock(EmployeeMemRepository.class);
    private final EmployeeMapper employeeMapper = new EmployeeMapper();
    private final EmployeeService employeeService = new EmployeeService(employeeMemRepository, employeeMapper);
    @Test
    public void whenDelete() {
        Input input = Mockito.mock(ConsoleInput.class);

        Mockito.when(input.askLong("Enter id: ")).thenReturn(1L);

        Mockito.when(employeeMemRepository.delete(ArgumentMatchers.any(Employee.class).getId())).thenReturn(employeeService.delete(1L));

        boolean deleteSuccess = deleteAction.execute(input, employeeService);
        Assert.assertTrue(deleteSuccess);
    }


}