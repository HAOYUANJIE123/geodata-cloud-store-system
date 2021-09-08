package test.controller;

import org.apache.log4j.Logger;
import test.dao.Agent1;
import test.dao.Agent_baseFunction;
import test.dao.PwdProtection;
import test.dao.agentCenter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by lai on 2019/4/17.
 */
@WebServlet(name = "PwdProtectionServlet",urlPatterns = {"/PwdProtectionServlet"})
public class PwdProtectionServlet extends HttpServlet {

    Logger logger = Logger.getLogger(getClass());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        try {
//            agentCenter.Adjust_agent("agentFindPwd",request,response,logger);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
           // Agent1.agentFindPwd(request,response);//调用agent方法找回密码
            Agent_baseFunction agent_baseFunction=new Agent_baseFunction();
            agent_baseFunction.agentFindPwd(request,response);//调用agent方法找回密码
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//
//        String usrnum = request.getParameter("usrnum"); //得到jsp页面传过来的参数
//        String question = request.getParameter("select-question");
//        String answer=request.getParameter("answer");
//
//        //判断注册界面要输入的文本框内容是否完整，否则弹报错信息
//        if(usrnum==null||usrnum==""){
//            request.setAttribute("xiaoxi", "账号为空");
//            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
//        }else if(question==null||question==""){
//            request.setAttribute("xiaoxi", "密保问题为空");
//            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
//        }else if(answer==null||answer==""){
//            request.setAttribute("xiaoxi", "密保答案为空");
//            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
//        }else{
//
//       String pwd= PwdProtection.getValue(usrnum,question,answer);//使用PwdProtection.getValue方法找回密码
//
//            if(pwd==""){
//                request.setAttribute("xiaoxi", "密码找回失败，请检验密保答案是否有误");
//                request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
//            }
//            else{
//                request.setAttribute("xiaoxi", pwd);
//                request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
//            }
//        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
