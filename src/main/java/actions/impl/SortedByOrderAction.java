package actions.impl;

import actions.EmployeeAction;
import input.Input;
import service.EmployeeService;
import sort.Ordered;
import util.OutputUtil;

public class SortedByOrderAction implements EmployeeAction {
    @Override
    public String name() {
        return "**** Sorted Employee By ASC/DESC ****";
    }

    @Override
    public boolean execute(Input input, EmployeeService employeeService) {
        String orderedType = input.askStr("Enter orderType. (ASC/DESC): ");
        try {
            OutputUtil.print(employeeService.findAll(Ordered.valueOf(orderedType)));
        } catch (IllegalArgumentException exception) {
            System.out.println("Incorrect order.");
            return true;
        }
        return true;
    }
}
