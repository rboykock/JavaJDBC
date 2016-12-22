import clazz.Employee;
import clazz.Salary;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by rboyko on 19.12.16.
 */
public class EmployeeDAO extends EntityDAO{
    protected  static final String SQL_INSERT="INSERT INTO EMPLOYEE (NAME) VALUES (?)";
    protected static final String SQL_UPDATE="UPDATE EMPLOYEE SET NAME=? WHERE id=?";
    protected static final String SQL_SELECT="SELECT id,name FROM EMPLOYEE";

    public  static void save(Employee employee){
        String sql="";
        if(employee.getId()==0)
            sql=SQL_INSERT;
        else
            sql=SQL_UPDATE;

        PreparedStatement preparedStatement= null;
        try {
            if(employee.getId()==0) {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,employee.getName());
                preparedStatement.executeUpdate();
                ResultSet resultSet=preparedStatement.getGeneratedKeys();
                if(resultSet.next())
                    employee.setId(resultSet.getInt(1));
            }else {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,employee.getName());
                preparedStatement.setInt(2,employee.getId());
                preparedStatement.executeUpdate();
            }
            preparedStatement.close();
        } catch (SQLException e) {
            Main.JDBCUtils.destroy();
            e.printStackTrace();
        }

    }

    public static ArrayList<Employee> getEmployees(){
        ArrayList<Employee> employees=new ArrayList<>();
        ResultSet resultSet=null;
        try {
            resultSet=connection.createStatement().executeQuery(SQL_SELECT);
            while (resultSet.next()){
                int id=resultSet.getInt(1);
                String name=resultSet.getString(2);
                Employee employee=new Employee(name);
                employee.setId(id);
                employees.add(employee);
            }
        } catch (SQLException e) {
            Main.JDBCUtils.destroy();
            e.printStackTrace();
        }

        return employees;
    }

}
