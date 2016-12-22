import clazz.Employee;
import clazz.Salary;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    static{
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected static final String SQL_QUERY_CREATE_TABLE_EMPLOYEE="CREATE TABLE IF NOT EXISTS employee(" +
            "  id INT AUTO_INCREMENT NOT NULL ," +
            "  name VARCHAR(255) NOT NULL," +
            "  PRIMARY KEY (id)" +
            ")";

    protected static final String SQL_QUERY_CREATE_TABLE_SELARY="CREATE TABLE IF NOT EXISTS salary (\n" +
            "  id INT AUTO_INCREMENT NOT NULL," +
            "  date BIGINT NOT NULL," +
            "  value DECIMAL NOT NULL," +
            "  emp_id INT NOT NULL," +
            "  PRIMARY KEY (id)," +
            "  FOREIGN KEY (emp_id) REFERENCES employee(id)" +
            ")";

    protected static final String URL="jdbc:h2:mem:geekhub";


    public static void main(String[] args) {
        Connection connection=JDBCUtils.getConnection(URL);
        createTable(connection);
        Employee employee_1=new Employee("Ivanov Ivan Ivanovich");
        Employee employee_2=new Employee("Petrov Petro Petrovich");

        EmployeeDAO.save(employee_1);
        EmployeeDAO.save(employee_2);

        employee_1.setSalary(new Salary(250.0));
        SalaryDAO.save(employee_1);
        employee_1.setSalary(new Salary(300.0));
        SalaryDAO.save(employee_1);
        employee_1.setSalary(new Salary(250.0));
        SalaryDAO.save(employee_1);
        employee_1.setSalary(new Salary(300.0));
        SalaryDAO.save(employee_1);
        employee_2.setSalary(new Salary(300.0));
        SalaryDAO.save(employee_1);

        SalaryDAO.save(employee_2);

        ArrayList<Employee> employees=EmployeeDAO.getEmployees();

        for (Employee employee:employees){
            System.out.println("Employee: "+employee.getName());
            ArrayList<Salary> salaries=SalaryDAO.getSalariesByEmployee(employee);
            printSalariesTable(salaries);
        }

        JDBCUtils.destroy();

    }

    public static void printSalariesTable(ArrayList<Salary> salaries){
        System.out.println("============= Salaries ======================");
        System.out.println("|           Date                   Value    |");
        System.out.println("=============================================");
        for(Salary salary:salaries)
            System.out.println("|"+new Date(salary.getDate())+"          "+salary.getValue()+"|");
        System.out.println("---------------------------------------------");
        Double total=salaries.stream().mapToDouble(s->s.getValue()).sum();
        System.out.println("| Total: " + total + "                             |");
        System.out.println("=============================================\n\n");
    }

    public static void createTable(Connection connection){
        Statement statement=null;
        try {
            statement=connection.createStatement();
            statement.execute(SQL_QUERY_CREATE_TABLE_EMPLOYEE);
            statement.execute(SQL_QUERY_CREATE_TABLE_SELARY);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static class JDBCUtils{
        protected static Connection connection=null;

        protected JDBCUtils(){

        }

        public static Connection getConnection(String url){
            if(JDBCUtils.connection==null)
                try {
                    JDBCUtils.connection=DriverManager.getConnection(url);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            return JDBCUtils.connection;
        }

        public static void destroy(){
            try {
                JDBCUtils.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
