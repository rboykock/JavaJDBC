import clazz.Employee;
import clazz.Salary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by rboyko on 19.12.16.
 */
public class SalaryDAO  extends EntityDAO{
    protected static final String SQL_INSERT="INSERT INTO SALARY (VALUE,DATE ,EMP_ID) VALUES (?,?,?)";
    protected static final String SQL_UPDATE="UPDATE SALARY SET VALUE=? WHERE id=?";
    protected static final String SQL_SELECT_SALARY_BY_ID="SELECT * FROM salary WHERE id=?";
    protected static final String SQL_SELECT_SALARY_BY_EMPID="SELECT * FROM salary WHERE emp_id=?";

    public static void save(Employee employee){
        PreparedStatement preparedStatement=null;
        try {
            if(employee.getSalary().getId()==0){
                preparedStatement=connection.prepareStatement(SQL_INSERT);
                preparedStatement.setDouble(1, employee.getSalary().getValue());
                preparedStatement.setLong(2,employee.getSalary().getDate());
                preparedStatement.setInt(3,employee.getId());
            } else {
                preparedStatement=connection.prepareStatement(SQL_UPDATE);
                preparedStatement.setDouble(1, employee.getSalary().getValue());
                preparedStatement.setInt(2, employee.getSalary().getId());
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Main.JDBCUtils.destroy();
            e.printStackTrace();
        }
    }

    public static ArrayList<Salary> getSalariesByEmployee(Employee employee){
        ResultSet resultSet=null;
        PreparedStatement preparedStatement=null;
        ArrayList<Salary> salaries=new ArrayList<>();
        try {
            preparedStatement=connection.prepareStatement(SQL_SELECT_SALARY_BY_EMPID);
            preparedStatement.setInt(1,employee.getId());
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                int id=resultSet.getInt(1);
                long date=resultSet.getLong(2);
                double value=resultSet.getDouble(3);

                Salary salary=new Salary(value);
                salary.setId(id);
                salary.setDate(date);
                salaries.add(salary);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            Main.JDBCUtils.destroy();
            e.printStackTrace();
        }

        return salaries;
    }
}
