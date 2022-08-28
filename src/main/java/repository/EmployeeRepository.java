package repository;

import model.Employee;
import util.Constants;

import java.util.Objects;

public class EmployeeRepository {
    private Employee[] employees = new Employee[10];
    int index = 0;

    /**
     * Метод сохранения сотрудника в репозиторий
     * @param employee сотрудник
     * @return employee
     */
    public Employee save(Employee employee) {
        if (employees.length - 1 == index) {
            System.out.println("Book size is max");
            employee.setId(Constants.INCORRECT_ID);
        } else {
            employees[index] = employee;
            index++;
        }
        return employee;
    }

    /**
     * Метод обновления данных сотрудника в репозитории
     * @param id сотрудника
     * @param employee сотрудник
     * @return true or false
     */
    public boolean update(long id, Employee employee) {
        for (int i = 0; i < employees.length; i++) {

            long employeeId = employees[i].getId();

            if (employeeId == id) {
                employee.setId(employeeId);
                employees[i] = employee;
                return true;
            }
        }
        return false;
    }

    /**
     * Метод удаления сотрудника из репозитория
     * @param id сотрудника
     * @return false
     */
    public boolean delete(long id) {
        for (int i = 0; i < employees.length; i++) {
            if (employees[i].getId() == id) {
                employees[i] = null;
                index--;
                return true;
            }
        }
        for (int i = 0; i < employees.length - 1; i++) {
            if (Objects.isNull(employees[i])) {
                employees[i] = employees[i + 1];
                employees[i + 1] = null;
            }
        }
        return false;
    }

    /**
     * Метод поиска сотрудника по id
     * @param id сотрудника
     * @return
     */
    public Employee findById(long id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

      public Employee[] findAllByName(String name) {
        return null;
    }

    /**
     * Метод поиска всех сотрудников
     * @return массив сотрудников
     */
    public Employee[] findAll() {
        int count = 0;
        for (Employee employee : employees) {
            if (Objects.nonNull(employee)) {
                count++;
            }
        }

        Employee[] result = new Employee[count];

        for (Employee employee : employees) {
            if (Objects.nonNull(employee)) {
                count--;
                result[count] = employee;
            }
        }
        return result;
    }
}
