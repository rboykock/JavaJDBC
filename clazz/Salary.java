package clazz;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by rboyko on 19.12.16.
 */
public class Salary {
    protected  int id;
    protected long date= new Timestamp(new Date().getTime()).getTime();
    protected double value;

    public Salary(double value){
        this.value=value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public long getDate() {
        return date;
    }


    public void setDate(long date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return id+" "+value+" "+date;
    }
}
