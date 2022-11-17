package actions.impl;

import dto.EmployeeDto;
import input.ConsoleInput;
import input.Input;
import mapper.EmployeeMapper;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import repository.impl.EmployeeMemRepository;
import service.EmployeeService;

import java.util.Optional;

public class FindByIdActionTest {
    private final FindByIdAction findByIdAction = new FindByIdAction();
    private final EmployeeMemRepository employeeMemRepository = Mockito.mock(EmployeeMemRepository.class);
    private final EmployeeMapper employeeMapper = new EmployeeMapper();
    private final EmployeeService employeeService = new EmployeeService(employeeMemRepository, employeeMapper);
    @Test
    public void whenFindById() {
        Input input = Mockito.mock(ConsoleInput.class);
        Mockito.when(input.askLong("Enter id: ")).thenReturn(1L);
        long id = input.askLong("Enter id: ");
        Optional<EmployeeDto> employee = Optional.of(new EmployeeDto("Nacta", "Moscva"));
       // Mockito.when(employeeMemRepository.findById(ArgumentMatchers.anyLong())).thenReturn(true);


        boolean findByIdSuccess = findByIdAction.execute(input, employeeService);
        Assert.assertTrue(findByIdSuccess);
    }
}