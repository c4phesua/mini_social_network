package daos;

import conn.SQLServerConnection;
import dtos.UserDTO;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    public static UserDTO checkLogin(String email, String psw) throws SQLException {
        UserDTO result = null;
        ResultSet resultSet = null;
        String sha256Psw = org.apache.commons.codec.digest.DigestUtils.sha256Hex(psw);
        try (Connection con = SQLServerConnection.getConnection();
             PreparedStatement stm = con.prepareStatement("SELECT ta.email, ta.usr_name, ta.usr_status " +
                     "FROM dbo.tblAccounts ta " +
                     "WHERE ta.email = ? AND ta.passwd = ?"))
        {
            stm.setString(1, email);
            stm.setString(2, sha256Psw);
            resultSet = stm.executeQuery();
            if (resultSet.next()){
                result = new UserDTO(resultSet.getNString(1),
                        resultSet.getNString(2), resultSet.getNString(3));
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return result;
    }
}
