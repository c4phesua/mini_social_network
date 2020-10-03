package daos;

import conn.SQLServerConnection;
import dtos.NotiDTO;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotiDAO {
    public static ArrayList<NotiDTO> getNotifications(String email){
        ArrayList<NotiDTO> result = new ArrayList<>();
        try (Connection con = SQLServerConnection.getConnection();
             PreparedStatement stm = con.prepareStatement(
                     "SELECT tn.actor, tn.post, tn.msg, tn.send_date\n" +
                             "FROM dbo.tblNotification tn\n" +
                             "WHERE tn.to_email = ?\n" +
                             "ORDER BY tn.send_date DESC"))
        {
            stm.setNString(1, email);
            try (ResultSet rs = stm.executeQuery()){
                while (rs.next()){
                    result.add(new NotiDTO(rs.getNString(1),
                            rs.getInt(2),
                            rs.getNString(3),
                            rs.getTimestamp(4)));
                }
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
