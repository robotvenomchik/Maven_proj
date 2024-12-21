package org.example.Homework29;

public class Main {

    public static void main(String[] args) {
        DatabaseInitializer.initializeDatabase();

        EmployeeDAO employeeDAO = new EmployeeDAO();
        employeeDAO.addEmployee("Joe Biden", 77, "democrat", 70000);
        employeeDAO.addEmployee("Donald trump", 75, "republican", 85000);

        System.out.println("All presidents:");
        employeeDAO.viewAllEmployees();

        employeeDAO.updateEmployee(3, "kamala Harris", 42, "democrat", 10000);

        System.out.println("After update:");
        employeeDAO.viewAllEmployees();

        employeeDAO.deleteEmployee(4);

        System.out.println("After delete:");
        employeeDAO.viewAllEmployees();
    }
}

