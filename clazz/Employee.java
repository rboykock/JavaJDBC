package clazz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rboyko on 19.12.16.
 */
public class Employee {
    protected int id=0;
    protected  String  name;
    protected Salary salary=null;

    public Employee(String name){
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary=salary;
    }
}
