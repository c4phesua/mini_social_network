package daos;

import conn.SQLServerConnection;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterDAO {
    public static boolean insertNewAccount(String email, String psw, String name) {
        String sha256Psw = org.apache.commons.codec.digest.DigestUtils.sha256Hex(psw);
        try (Connection con = SQLServerConnection.getConnection();
            PreparedStatement stm = con.prepareStatement("INSERT\tdbo.tblAccounts\n" +
                    "(\n" +
                    "    email,\n" +
                    "    usr_name,\n" +
                    "    passwd,\n" +
                    "    usr_role,\n" +
                    "    usr_status\n" +
                    ")\n" +
                    "VALUES\n" +
                    "(\n" +
                    "    ?, -- email - nvarchar\n" +
                    "    ?, -- usr_name - nvarchar\n" +
                    "    ?, -- passwd - nvarchar\n" +
                    "    1, -- usr_role - bit\n" +
                    "    N'new' -- usr_status - nvarchar\n" +
                    ")"))
        {
            stm.setNString(1, email);
            stm.setNString(2, name);
            stm.setNString(3, sha256Psw);
            stm.execute();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
