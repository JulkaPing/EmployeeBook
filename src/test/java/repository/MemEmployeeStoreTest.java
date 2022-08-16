package repository;

import model.Employee;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MemEmployeeStoreTest {

    @Test
    public void whenSave() {
        MemEmployeeStore memEmployeeStore = new MemEmployeeStore();
        Employee employee = new Employee("Nikita", "Russia");
        memEmployeeStore.save(employee);
        Employee result = memEmployeeStore.findById(employee.getId());
        Assert.assertNotNull(result);
    }

}