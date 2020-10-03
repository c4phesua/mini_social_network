package controllers;

import daos.SystemDAO;
import daos.UserDAO;
import dtos.UserDTO;
import utils.StringLib;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommentController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("txtPostId");
        UserDTO user = (UserDTO) req.getSession().getAttribute("userInfo");
        if (user == null){
            resp.sendRedirect("index.jsp");
            return;
        }
        int postId = Integer.parseInt(id);
        String email = user.getEmail();
        String text = StringLib.toUnicode(req.getParameter("txtComment"));
        UserDAO.postComment(email, postId, text);
        String notificationMess = "New comment in your post";
        SystemDAO.sendNotification(postId, email, notificationMess);
        resp.sendRedirect("detail?id=" + postId);
    }
}
