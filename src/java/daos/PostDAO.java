package daos;

import conn.SQLServerConnection;
import dtos.CommentDTO;
import dtos.PostDTO;

import javax.naming.NamingException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;

public class PostDAO {
    public static boolean insertNewPost(String title, String desc, String email, InputStream img) {
        try (Connection con = SQLServerConnection.getConnection();
             PreparedStatement stm = con.prepareStatement("INSERT dbo.tblPosts\n" +
                     "(\n" +
                     "    --post_id - column value is auto-generated\n" +
                     "    email,\n" +
                     "    title,\n" +
                     "    post_desc,\n" +
                     "    img,\n" +
                     "    create_date\n" +
                     ")\n" +
                     "VALUES\n" +
                     "(\n" +
                     "    -- post_id - bigint\n" +
                     "    ?, -- email - nvarchar\n" +
                     "    ?, -- title - nvarchar\n" +
                     "    ?, -- post_desc - nvarchar\n" +
                     "    ?, -- img - varbinary\n" +
                     "    ? -- create_date - datetime\n" +
                     ")")) {
            Timestamp dateTime = new Timestamp(System.currentTimeMillis());
            stm.setNString(1, email);
            stm.setNString(2, title);
            stm.setNString(3, desc);
            stm.setBinaryStream(4, img);
            stm.setTimestamp(5, dateTime);
            stm.execute();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ArrayList<PostDTO> getPosts(int offset, int amount) {
        ArrayList<PostDTO> posts = new ArrayList<>();
        try (Connection con = SQLServerConnection.getConnection();
             PreparedStatement stm = con.prepareStatement(
                     "SELECT\ttp.post_id, tp.email, tp.title, tp.post_desc, tp.img, tp.create_date\n" +
                             "FROM dbo.tblPosts tp\n" +
                             "WHERE tp.post_status = 1\n" +
                             "ORDER BY tp.create_date DESC\n" +
                             "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY")) {
            stm.setInt(1, offset);
            stm.setInt(2, amount);
            try (ResultSet resultSet = stm.executeQuery()) {
                while (resultSet.next()) {
                    PostDTO post = new PostDTO(resultSet.getBigDecimal(1),
                            resultSet.getNString(2),
                            resultSet.getNString(3),
                            resultSet.getNString(4),
                            resultSet.getBytes(5),
                            resultSet.getTimestamp(6));
                    posts.add(post);
                }
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return posts;
    }

    public static ArrayList<PostDTO> getPosts(String email) {
        ArrayList<PostDTO> posts = new ArrayList<>();
        try (Connection con = SQLServerConnection.getConnection();
             PreparedStatement stm = con.prepareStatement(
                     "SELECT\ttp.post_id, tp.email, tp.title, tp.post_desc, tp.img, tp.create_date\n" +
                             "FROM dbo.tblPosts tp\n" +
                             "WHERE tp.email = ? AND tp.post_status = 1\n" +
                             "ORDER BY tp.create_date DESC")) {
            stm.setNString(1, email);
            try (ResultSet resultSet = stm.executeQuery()) {
                while (resultSet.next()) {
                    PostDTO post = new PostDTO(resultSet.getBigDecimal(1),
                            resultSet.getNString(2),
                            resultSet.getNString(3),
                            resultSet.getNString(4),
                            resultSet.getBytes(5),
                            resultSet.getTimestamp(6));
                    posts.add(post);
                }
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return posts;
    }

    public static ArrayList<PostDTO> searchPosts(String keywork) {
        ArrayList<PostDTO> posts = new ArrayList<>();
        try (Connection con = SQLServerConnection.getConnection();
             PreparedStatement stm = con.prepareStatement(
                     "SELECT\ttp.post_id, tp.email, tp.title, tp.post_desc, tp.img, tp.create_date\n" +
                             "FROM dbo.tblPosts tp\n" +
                             "WHERE tp.post_desc LIKE '%' + ? + '%'\n AND tp.post_status = 1" +
                             "ORDER BY tp.create_date DESC")) {
            stm.setNString(1, keywork);
            try (ResultSet resultSet = stm.executeQuery()) {
                while (resultSet.next()) {
                    PostDTO post = new PostDTO(resultSet.getBigDecimal(1),
                            resultSet.getNString(2),
                            resultSet.getNString(3),
                            resultSet.getNString(4),
                            resultSet.getBytes(5),
                            resultSet.getTimestamp(6));
                    posts.add(post);
                }
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return posts;
    }

    public static PostDTO getPostDetail(int postId) {
        PostDTO postDTO = null;
        try (Connection con = SQLServerConnection.getConnection();
             PreparedStatement stmPostInfo = con.prepareStatement(
                     "SELECT tp.email, tp.title, tp.post_desc, tp.img, tp.create_date\n" +
                             "FROM dbo.tblPosts tp\n" +
                             "WHERE tp.post_id = ? AND tp.post_status = 1");
             PreparedStatement stmPostComment = con.prepareStatement(
                     "SELECT tc.email, tc.cmt_text\n" +
                             "FROM dbo.tblComments tc\n" +
                             "WHERE tc.post_id = ?");
             PreparedStatement stmPostEmotion = con.prepareStatement(
                     "SELECT te.email, te.emt_value\n" +
                             "FROM dbo.tblEmotions te\n" +
                             "WHERE te.post_id = ?")
        )
        {
            stmPostInfo.setInt(1, postId);
            stmPostComment.setInt(1, postId);
            stmPostEmotion.setInt(1, postId);
            try (ResultSet rsPostInfo = stmPostInfo.executeQuery();
                ResultSet rsPostComment = stmPostComment.executeQuery();
                ResultSet rsPostEmotion = stmPostEmotion.executeQuery())
            {
                if (rsPostInfo.next()){
                    postDTO = new PostDTO(new BigDecimal(postId),
                            rsPostInfo.getNString(1), rsPostInfo.getNString(2),
                            rsPostInfo.getNString(3), rsPostInfo.getBytes(4),
                            rsPostInfo.getTimestamp(5));
                    ArrayList<CommentDTO> comments = new ArrayList<>();
                    while (rsPostComment.next()){
                        comments.add(new CommentDTO(rsPostComment.getNString(1), rsPostComment.getNString(2)));
                    }
                    postDTO.setComments(comments);
                    HashSet<String> likes = new HashSet<>();
                    HashSet<String> dislikes = new HashSet<>();
                    while (rsPostEmotion.next()){
                        String email = rsPostEmotion.getNString(1);
                        int value = rsPostEmotion.getInt(2);
                        if (value == 1){
                            likes.add(email);
                        }
                        else if (value == -1){
                            dislikes.add(email);
                        }
                    }
                    postDTO.setLikes(likes);
                    postDTO.setDisLikes(dislikes);
                }
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return postDTO;
    }

    public static void deletePost(int postId){
        try (Connection con = SQLServerConnection.getConnection();
            PreparedStatement stm = con.prepareStatement(
                    "UPDATE dbo.tblPosts\n" +
                            "SET\n" +
                            "    dbo.tblPosts.post_status = 0 -- bit\n" +
                            "WHERE dbo.tblPosts.post_id = ?"))
        {
            stm.setInt(1, postId);
            stm.execute();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }
}
