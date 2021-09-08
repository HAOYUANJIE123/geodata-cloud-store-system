package test.controller;

import test.dao.Search;
import test.vo.DataClass;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by lai on 2019/5/12.
 */
@WebServlet(name = "SearchUserServlet",urlPatterns = {"/SearchUserServlet"})
public class SearchUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        List<DataClass> userAll = Search.getUserValue();
        request.setAttribute("userAll",userAll);
        request.getRequestDispatcher("/download.jsp").forward(request, response);
    }
}
