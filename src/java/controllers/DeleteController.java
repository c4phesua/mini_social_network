package controllers;

import daos.PostDAO;
import utils.SystemLib;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteController extends HttpServlet {
    private static final String SUCCESS = "home.jsp";
    private static final String FAIL = "error.jsp";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = FAIL;
        try {
            int postId = Integer.parseInt(req.getParameter("txtId"));
            PostDAO.deletePost(postId);
            url = SUCCESS;
            SystemLib.updateUserPosts(req);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        resp.sendRedirect(url);
    }
}
