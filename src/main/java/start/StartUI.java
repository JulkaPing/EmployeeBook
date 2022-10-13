package start;

import actions.EmployeeAction;
import actions.impl.*;
import input.ConsoleInput;
import input.Input;
import mapper.EmployeeMapper;
import actions.*;
import model.Employee;
import repository.Repository;
import repository.impl.EmployeeMemRepository;
import service.EmployeeService;

public class StartUI {
    public void init(Input input, EmployeeService employeeService, EmployeeAction[] employeeActions) {
        boolean isRunning = true;
        while (isRunning) {
            showMenu(employeeActions);
            int select = (int) input.askLong("Select: ");
            if ( select < 0 || select >= employeeActions.length) {
                System.out.println("Wrong input, you can select: 0 ..." + (employeeActions.length - 1));
                continue;
            }
            EmployeeAction action = employeeActions[select];
            isRunning = action.execute(input, employeeService);
        }
    }

    private void showMenu(EmployeeAction[] employeeActions) {
        System.out.println("Welcome. It is menu.");
        for ( int index = 0; index < employeeActions.length; index++) {
            System.out.println(index + ". " + employeeActions[index].name());
        }
    }

    public static void main(String[] args) {
        final Input input = new ConsoleInput();
        final Repository<Long, Employee> store = new EmployeeMemRepository();
        final EmployeeService employeeService = new EmployeeService(store, new EmployeeMapper());
        final EmployeeAction[] actions = {
                new CreateAction(),
                new UpdateAction(),
                new DeleteAction(),
                new FindAllAction(),
                new FindByIdAction(),
                new FindByIntervalDateAction(),
                new FindByNameAction(),
                new SortedByOrderAction(),
                new ExitProgramAction()
        };
        new StartUI().init(input, employeeService, actions);
    }
}
