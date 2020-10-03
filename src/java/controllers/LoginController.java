package controllers;

import daos.LoginDAO;
import dtos.UserDTO;
import utils.SystemLib;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController extends HttpServlet {
    private static final String SUCCESS = "home.jsp";
    private static final String FAIL = "index.jsp";
    private static final String ERROR = "error.jsp";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("txtEmail");
        String pass = req.getParameter("txtPassword");
        String url = ERROR;
        try {
            UserDTO user = LoginDAO.checkLogin(email, pass);
            if (user == null){
                url = FAIL;
                req.setAttribute("loginErrorMsg", "Incorrect email or password");
            }
            else {
                req.getSession().setAttribute("is_login", true);
                req.getSession().setAttribute("userInfo", user);
                url = SUCCESS;
                SystemLib.updateUserPosts(req);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!url.equals(FAIL)){
            resp.sendRedirect(url);
        }
        else {
            req.getRequestDispatcher(url).forward(req, resp);
        }
    }
}
