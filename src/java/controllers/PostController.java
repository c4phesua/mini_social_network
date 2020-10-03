package controllers;

import daos.PostDAO;
import dtos.UserDTO;
import utils.StringLib;
import utils.SystemLib;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

public class PostController extends HttpServlet {
    private static final String HOME = "home.jsp";
    private static final String ERROR = "error.jsp";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = StringLib.toUnicode(req.getParameter("txtTitle"));
        String desc = StringLib.toUnicode(req.getParameter("txtDesc"));
        UserDTO user = (UserDTO) req.getSession().getAttribute("userInfo");
        if (user == null){
            resp.sendRedirect("index.jsp");
            return;
        }
        String email = user.getEmail();
        Part filePart = req.getPart("image");
        InputStream fileContent = filePart.getInputStream();
        boolean result = PostDAO.insertNewPost(title, desc, email, fileContent);
        if (result){
            SystemLib.updateUserPosts(req);
            resp.sendRedirect(HOME);
        }
        else {
            resp.sendRedirect(ERROR);
        }
    }
}
