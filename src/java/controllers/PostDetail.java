package controllers;

import daos.PostDAO;
import dtos.PostDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostDetail extends HttpServlet {
    private static final String SUCCESS = "template.jsp";
    private static final String FAIL = "error.jsp";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = FAIL;
        try {
            String id = req.getParameter("id");
            int postId = Integer.parseInt(id);
            PostDTO post = PostDAO.getPostDetail(postId);
            req.setAttribute("post", post);
            url = SUCCESS;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        req.getRequestDispatcher(url).forward(req, resp);
    }
}
