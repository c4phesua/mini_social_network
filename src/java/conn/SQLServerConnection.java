package conn;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLServerConnection {
    public static Connection getConnection() throws NamingException, SQLException {
        InitialContext ic = new InitialContext();
        DataSource dc = (DataSource) ic.lookup("java:comp/env/jdbc/MiniSociety");
        return dc.getConnection();
    }
}
