package utils;

import daos.NotiDAO;
import daos.PostDAO;
import dtos.NotiDTO;
import dtos.PostDTO;
import dtos.UserDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class SystemLib {
    public static void updateNotification(HttpServletRequest req){
        UserDTO user = (UserDTO) req.getSession().getAttribute("userInfo");
        if (user != null){
            String email = user.getEmail();
            ArrayList<NotiDTO> notifications = NotiDAO.getNotifications(email);
            user.setNotifications(notifications);
        }
    }

    public static void updateUserPosts(HttpServletRequest req){
        UserDTO user = (UserDTO) req.getSession().getAttribute("userInfo");
        if (user != null){
            String email = user.getEmail();
            ArrayList<PostDTO> posts = PostDAO.getPosts(email);
            user.setPosts(posts);
        }
    }
}
