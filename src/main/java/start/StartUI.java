package start;

import model.Employee;
import service.EmployeeService;

import java.util.Scanner;

public class StartUI {

    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeService();
        Scanner scanner = new Scanner(System.in);

        boolean isRunning = true;

        while (isRunning) {
            StartUI.showMenu();
            System.out.println("Enter value of menu");
            int value = scanner.nextInt();

            if (value == 1) {
                System.out.println("Enter name");
                String name = scanner.next();

                System.out.println("Enter country");
                String country = scanner.next();

                Employee employee = new Employee(name, country);
                employeeService.save(employee);
            } else if (value == 2) {
                System.out.println("Enter id");
                long id = scanner.nextLong();
                employeeService.delete(id);
            } else if ( value == 3) {
                for (Employee employee : employeeService.findAll()) {
                    System.out.println(employee);
                }
            } else if (value == 4) {
                isRunning = false;
                System.out.println("Close program.");
            }
        }

    }


    private static void showMenu() {
        System.out.println("1. Save employee.");
        System.out.println("2. Delete employee.");
        System.out.println("3. Find employee.");
        System.out.println("4. Close program.");
    }
}
