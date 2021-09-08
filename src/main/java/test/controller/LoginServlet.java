package test.controller;

import org.apache.log4j.Logger;
import test.dao.*;
import test.factor.GsonFactory;
import test.vo.FriendClass;
import test.vo.Message;
import test.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by lai on 2019/4/17.
 */
@WebServlet(name = "LoginServlet",urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    Logger logger = Logger.getLogger(getClass());
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


//        try {
//            agentCenter.Adjust_agent("agentLogin_in",request,response,logger);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }


        try {
            Agent_baseFunction agent_baseFunction=new Agent_baseFunction();
            agent_baseFunction.agentLogin_in( request,response);//调用agent方法登录
            //Agent1.agentLogin_in( request,response);//调用agent方法登录//
        } catch (SQLException e) {
            e.printStackTrace();
        }


//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//
//        String id = request.getParameter("userid"); //后端获取登陆界面sign_in.html的用户账号，密码，以及用户类型的值
//        String pwd = request.getParameter("password");
//        String radio=request.getParameter("radioname");
//
//        //判断账号密码是否填完整
//        if(id==null||id==""){
//            request.setAttribute("xiaoxi", "账号为空");
//            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
//        }else if(pwd==null||pwd==""){
//            request.setAttribute("xiaoxi", "密码为空");
//            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
//        }else if(radio==null||radio==""){
//            request.setAttribute("xiaoxi", "用户类型为空");
//            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
//        }else{
//
//            if(Login.getValue(id, pwd,radio)){//使用Login.getValue方法验证登录账号，密码，验证成功后；进行下面工作：
//
//
//                //将User对象的其它字段进行初始化
//                User.setLocality("");
//                User.setType("");
//                User.setCxtime("");
//                User.setWn_coord("");
//                User.setEs_coord("");
//                User.setFriend("");
//                FriendClass.setFriendid("");
//                FriendClass.setFriendname("");
//
//                User.setRw_name("");
//                User.setRwskill("");
//                User.setPay("");
//                User.setUrl("");
//                User.setDescri("");
//                User.setSj_id("");
//                response.sendRedirect("search.jsp");
//            }else{
//                response.sendRedirect("sign_in.html"); //重定向到首页
//            }
//        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
