package controllers;

import dtos.PostDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class HomeController extends HttpServlet {
    private static final String HOME = "home.jsp";
    private static final String ERROR = "error.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = ERROR;
        try {
            int offset;
            String offsetString = req.getParameter("offset");
            if (offsetString == null) {
                offset = 0;
            }
            else {
                offset = Integer.parseInt(offsetString) + 20;
            }
            ArrayList<PostDTO> posts = daos.PostDAO.getPosts(offset, 20);
            req.setAttribute("posts", posts);
            req.setAttribute("offset", offset);
            url = HOME;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            req.getRequestDispatcher(url).forward(req, resp);
        }
    }
}
