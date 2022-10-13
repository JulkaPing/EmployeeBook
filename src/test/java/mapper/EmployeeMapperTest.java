package mapper;

import dto.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import model.Employee;
import org.junit.Assert;
import org.junit.Test;

import java.time.format.DateTimeParseException;

@Slf4j
public class EmployeeMapperTest {
    private final EmployeeMapper employeeMapper = new EmployeeMapper();

    @Test
    public void fromEmployeeToEmployeeDto() {
        try {
            Employee employee = new Employee("Nikita", "Moscow");
            log.info("Employee DTO - {}", employeeMapper.fromEmployeeToEmployeeDto(employee));
        } catch (Exception exception) {
            Assert.fail();
        }
    }

    @Test
    public void fromEmployeeDtoToEmployee() {
        try {
            EmployeeDto employeeDto = new EmployeeDto("Nikita", "Moscow");
            log.info("Employee DTO - {}", employeeMapper.fromEmployeeDtoToEmployeeEntity(employeeDto));
        } catch (Exception exception) {
            Assert.fail();
        }
    }

    @Test
    public void parseDate() {
        try {
            String date = "20-09-2022 03:08";
            employeeMapper.parseDate(date);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(expected = DateTimeParseException.class)
    public void whenParseWithException() {
        employeeMapper.parseDate("sdfghjk");
    }

}