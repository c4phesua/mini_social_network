package controllers;

import daos.RegisterDAO;
import utils.StringLib;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterController extends HttpServlet {
    private static final String URL = "index.jsp";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("txtEmail");
        String name = req.getParameter("txtName");
        name = StringLib.toUnicode(name);
        String pass = req.getParameter("txtPassword");
        boolean is_success = RegisterDAO.insertNewAccount(email, pass, name);
        if (is_success){
            req.setAttribute("registerMsg", "Successful registration, now you can login");
            req.getRequestDispatcher(URL).forward(req, resp);
        }
        else {
            req.setAttribute("registerMsg", "Registration failed, please try to create an account with another email account");
            req.getRequestDispatcher(URL).forward(req, resp);
        }
    }
}
