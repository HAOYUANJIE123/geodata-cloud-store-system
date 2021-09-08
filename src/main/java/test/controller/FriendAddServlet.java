package test.controller;

import org.apache.log4j.Logger;
import test.dao.Friend;
import test.dao.Search;
import test.vo.DataClass;
import test.vo.FriendInfoClass;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lai on 2019/5/11.
 */
@WebServlet(name = "FriendAddServlet",urlPatterns = {"/FriendAddServlet"})
public class FriendAddServlet extends HttpServlet {
    Logger logger = Logger.getLogger(getClass());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        List<FriendInfoClass> userNew= Friend.getNewFriend();//将gxb的好友信息赋给usernew
        request.setAttribute("userNew",userNew);

        List<FriendInfoClass> userAll= Friend.getFriendInfo();
        request.setAttribute("userAll",userAll);
        request.getRequestDispatcher("/user.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String friendid=request.getParameter("friendid");
        Friend.addFriend(friendid);

        List<FriendInfoClass> userNew= Friend.getNewFriend();
        request.setAttribute("userNew",userNew);
        List<FriendInfoClass> userAll= Friend.getFriendInfo();
        request.setAttribute("userAll",userAll);
        request.getRequestDispatcher("/user.jsp").forward(request, response);
    }
}
