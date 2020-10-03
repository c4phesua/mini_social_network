package controllers;

import daos.PostDAO;
import dtos.PostDTO;
import utils.StringLib;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class SearchController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("is_login") == null){
            resp.sendRedirect("index.jsp");
            return;
        }
        String keyword = StringLib.toUnicode(req.getParameter("txtKeyword"));
        ArrayList<PostDTO> posts = PostDAO.searchPosts(keyword);
        req.setAttribute("offset", -20);
        req.setAttribute("posts", posts);
        req.getRequestDispatcher("home.jsp").forward(req, resp);
    }
}
