package daos;

import conn.SQLServerConnection;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static void postComment(String email, int postId, String text){
        try (Connection con = SQLServerConnection.getConnection();
             PreparedStatement stm = con.prepareStatement("INSERT dbo.tblComments\n" +
                     "(\n" +
                     "    --cmt_id - column value is auto-generated\n" +
                     "    email,\n" +
                     "    post_id,\n" +
                     "    cmt_text\n" +
                     ")\n" +
                     "VALUES\n" +
                     "(\n" +
                     "    -- cmt_id - bigint\n" +
                     "    ?, -- email - nvarchar\n" +
                     "    ?, -- post_id - bigint\n" +
                     "    ? -- cmt_text - nvarchar\n" +
                     ")"))
        {
            stm.setNString(1, email);
            stm.setInt(2, postId);
            stm.setNString(3, text);
            stm.execute();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    public static void makeEmotion(String email, int postId, int value){
        try (Connection con = SQLServerConnection.getConnection();
             PreparedStatement stmChecker = con.prepareStatement(
                     "SELECT te.emt_id FROM dbo.tblEmotions te\n" +
                     "WHERE te.email = ? AND te.post_id = ?");
             PreparedStatement stmAddNew = con.prepareStatement(
                     "INSERT dbo.tblEmotions\n" +
                             "(\n" +
                             "    --emt_id - column value is auto-generated\n" +
                             "    email,\n" +
                             "    post_id,\n" +
                             "    emt_value\n" +
                             ")\n" +
                             "VALUES\n" +
                             "(\n" +
                             "    -- emt_id - bigint\n" +
                             "    ?, -- email - nvarchar\n" +
                             "    ?, -- post_id - bigint\n" +
                             "    ? -- emt_value - int\n" +
                             ")");
             PreparedStatement stmChange = con.prepareStatement(
                     "UPDATE dbo.tblEmotions\n" +
                             "SET\n" +
                             "   dbo.tblEmotions.emt_value = ?\n" +
                             "WHERE dbo.tblEmotions.email = ? AND dbo.tblEmotions.post_id = ?")
        )
        {
            stmChecker.setNString(1, email);
            stmChecker.setInt(2, postId);
            try (ResultSet ckResult = stmChecker.executeQuery()){
                if (ckResult.next()){
                    stmChange.setInt(1, value);
                    stmChange.setNString(2, email);
                    stmChange.setInt(3, postId);
                    stmChange.execute();
                }
                else {
                    stmAddNew.setNString(1, email);
                    stmAddNew.setInt(2, postId);
                    stmAddNew.setInt(3, value);
                    stmAddNew.execute();
                }
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }
}
