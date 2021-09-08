package test.controller;

import org.apache.log4j.Logger;
import test.dao.Friend;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lai on 2019/5/11.
 */
@WebServlet(name = "FriendSearchServlet",urlPatterns = {"/FriendSearchServlet"})
public class FriendSearchServlet extends HttpServlet {
    Logger logger = Logger.getLogger(getClass());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        Friend.requestFriend();
        request.getRequestDispatcher("/user.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String friendid=request.getParameter("friendid");
        Friend.getFriendName(friendid);
        request.getRequestDispatcher("/user.jsp").forward(request, response);
    }
}
