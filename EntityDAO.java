import java.sql.Connection;

/**
 * Created by rboyko on 19.12.16.
 */
public class EntityDAO {
    protected static Connection connection= Main.JDBCUtils.getConnection(Main.URL);
}
