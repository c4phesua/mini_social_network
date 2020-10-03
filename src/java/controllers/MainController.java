package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
public class MainController extends HttpServlet {
    private static final String ERROR = "error.jsp";
    private static final String LOGIN = "login";
    private static final String REGISTER = "register";
    private static final String POST = "post";
    private static final String COMMENT = "cmt";
    private static final String EMOTION = "emo";
    private static final String DELETE = "delete";
    private static final String SEARCH = "searching";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String url = ERROR;
        try {
            String action = req.getParameter("btnAction");
            switch (action) {
                case "Login":
                    url = LOGIN;
                    break;
                case "Register":
                    url = REGISTER;
                    break;
                case "Post":
                    url = POST;
                    break;
                case "Comment":
                    url = COMMENT;
                    break;
                case "Emotion":
                    url = EMOTION;
                    break;
                case "Delete":
                    url = DELETE;
                    break;
                case "Search":
                    url = SEARCH;
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            req.getRequestDispatcher(url).forward(req, resp);
        }
    }
}
