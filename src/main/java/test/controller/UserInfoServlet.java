package test.controller;

import test.dao.Search;
import test.dao.Task;
import test.vo.DataClass;
import test.vo.User;
import test.vo.UserClass;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by lai on 2019/5/21.
 */
@WebServlet(name = "UserInfoServlet",urlPatterns = {"/UserInfoServlet"})
public class UserInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String sj_id=request.getParameter("sj_id");

        User.setSj_id(sj_id);
        List<UserClass> userAll= Task.getUserInfo(sj_id);
        request.setAttribute("userAll",userAll);
        request.getRequestDispatcher("/GIS.jsp").forward(request, response);
    }
}
