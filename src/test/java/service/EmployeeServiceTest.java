package service;

import dto.EmployeeDto;
import mapper.EmployeeMapper;
import org.junit.Assert;
import org.junit.Test;
import repository.impl.EmployeeMemRepository;
import sort.Ordered;
import java.util.List;

public class EmployeeServiceTest {
    private final EmployeeMapper employeeMapper = new EmployeeMapper();
    private final EmployeeService employeeService = new EmployeeService(new EmployeeMemRepository(), employeeMapper);

    @Test
    public void whenSortingDefault() {
        EmployeeDto first = new EmployeeDto("Nikita", "Moscow");
        EmployeeDto second = new EmployeeDto("Arina", "Moscow");
        EmployeeDto third = new EmployeeDto("Boris", "St. Petersburg");
        employeeService.save(first);
        employeeService.save(second);
        employeeService.save(third);

        List<EmployeeDto> expected = List.of(first, second, third);
        List<EmployeeDto> result = employeeService.findAll(Ordered.DEFAULT);
        Assert.assertEquals("Nikita", result.get(0).getName());
        Assert.assertEquals("Arina", result.get(1).getName());
        Assert.assertEquals("Boris", result.get(2).getName());
        Assert.assertEquals(3, result.size());
    }

    @Test
    public void whenSortingDesc() {
        EmployeeDto first = new EmployeeDto("Nikita", "Moscow");
        EmployeeDto second = new EmployeeDto("Arina", "Moscow");
        EmployeeDto third = new EmployeeDto("Boris", "St. Petersburg");
        employeeService.save(first);
        employeeService.save(second);
        employeeService.save(third);

        List<EmployeeDto> expected = List.of(first, second, third);
        List<EmployeeDto> result = employeeService.findAll(Ordered.DESC);
        Assert.assertEquals("Nikita", result.get(0).getName());
        Assert.assertEquals("Boris", result.get(1).getName());
        Assert.assertEquals("Arina", result.get(2).getName());
        Assert.assertEquals(3, result.size());
    }

    @Test
    public void whenSortingAsc() {
        EmployeeDto first = new EmployeeDto("Nikita", "Moscow");
        EmployeeDto second = new EmployeeDto("Arina", "Moscow");
        EmployeeDto third = new EmployeeDto("Boris", "St. Petersburg");
        employeeService.save(first);
        employeeService.save(second);
        employeeService.save(third);

        List<EmployeeDto> expected = List.of(first, second, third);
        List<EmployeeDto> result = employeeService.findAll(Ordered.ASC);
        Assert.assertEquals("Arina", result.get(0).getName());
        Assert.assertEquals("Boris", result.get(1).getName());
        Assert.assertEquals("Nikita", result.get(2).getName());
        Assert.assertEquals(3, result.size());
    }
}