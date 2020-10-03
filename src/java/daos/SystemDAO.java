package daos;

import conn.SQLServerConnection;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SystemDAO {
    public static void sendNotification(int postId, String actor, String mess){
        try (Connection con = SQLServerConnection.getConnection();
             PreparedStatement stmGetEmail = con.prepareStatement(
                     "SELECT tp.email\n" +
                             "FROM dbo.tblPosts tp\n" +
                             "WHERE tp.post_id = ?");
             PreparedStatement stmAdd = con.prepareStatement(
                     "INSERT dbo.tblNotification\n" +
                             "(\n" +
                             "    --ntf_id - column value is auto-generated\n" +
                             "    to_email,\n" +
                             "    actor,\n" +
                             "    post,\n" +
                             "    msg,\n" +
                             "    send_date\n" +
                             ")\n" +
                             "VALUES\n" +
                             "(\n" +
                             "    -- ntf_id - bigint\n" +
                             "    ?, -- to_email - nvarchar\n" +
                             "    ?, -- actor - nvarchar\n" +
                             "    ?, -- post - bigint\n" +
                             "    ?, -- msg - nvarchar\n" +
                             "    ? -- send_date - datetime\n" +
                             ")")
        )
        {
            stmGetEmail.setInt(1, postId);
            try (ResultSet rsEmail = stmGetEmail.executeQuery())
            {
                if (rsEmail.next()){
                    String email = rsEmail.getNString(1);
                    Timestamp now = new Timestamp(System.currentTimeMillis());
                    stmAdd.setNString(1, email); //set email that receive notification
                    stmAdd.setNString(2, actor); //set email that make the action to the post
                    stmAdd.setInt(3, postId);
                    stmAdd.setNString(4, mess);
                    stmAdd.setTimestamp(5, now);
                    stmAdd.execute();
                }
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }
}
