package controllers;

import daos.SystemDAO;
import daos.UserDAO;
import dtos.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmotionController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO user = (UserDTO) req.getSession().getAttribute("userInfo");
        if (user == null){
            resp.sendRedirect("index.jsp");
            return;
        }
        int postId = Integer.parseInt(req.getParameter("txtPostId"));
        int value = Integer.parseInt(req.getParameter("txtValue"));
        String email = user.getEmail();
        UserDAO.makeEmotion(email, postId, value);
        String notificationMess = "New emotion in your post";
        SystemDAO.sendNotification(postId, email, notificationMess);
        resp.sendRedirect("detail?id=" + postId);
    }
}
