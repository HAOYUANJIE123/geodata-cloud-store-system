package test.controller;

import org.apache.log4j.Logger;
import test.dao.Agent1;
import test.dao.Agent_baseFunction;
import test.dao.Register;
import test.dao.agentCenter;
import test.service.GetFriendInfo;
import test.vo.UserClass;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by lai on 2019/4/17.
 */
@WebServlet(name = "RegisterServlet",urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    Logger logger = Logger.getLogger(getClass());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


//        try {
//            agentCenter.Adjust_agent("agentRegister",request,response,logger);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        try {
        //    Agent1.agentRegister(request,response);//调用agengt注册
            Agent_baseFunction agent_baseFunction=new Agent_baseFunction();
            agent_baseFunction.agentRegister(request,response);//调用agengt注册
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//
//        String usrnum = request.getParameter("usrnum"); //得到jsp页面传过来的参数
//        String username = request.getParameter("username");
//        String pwd = request.getParameter("usrpwd");
//        String radio=request.getParameter("select-type");
//        String question = request.getParameter("select-question");
//        String answer=request.getParameter("answer");
//        String checkbox1=request.getParameter("checkbox1");
//        String checkbox2=request.getParameter("checkbox2");
//        String checkbox3=request.getParameter("checkbox3");
//        String checkbox4=request.getParameter("checkbox4");
//        String checkbox5=request.getParameter("checkbox5");
//
//
//        if(usrnum==null||usrnum==""){
//            request.setAttribute("xiaoxi", "账号为空");
//            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
//        }else if(username==null||username==""){
//            request.setAttribute("xiaoxi", "用户名为空");
//            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
//        }else if(pwd==null||pwd==""){
//            request.setAttribute("xiaoxi", "密码为空");
//            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
//        }else if(radio==null||radio==""){
//            request.setAttribute("xiaoxi", "用户类型为空");
//            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
//        }else if(question==null||question==""){
//            request.setAttribute("xiaoxi", "密保问题为空");
//            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
//        }else if(answer==null||answer==""){
//            request.setAttribute("xiaoxi", "密保答案为空");
//            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
//        }
//        else if(checkbox1=="" && checkbox2=="" && checkbox3=="" && checkbox4=="" && checkbox5==""){
//            request.setAttribute("xiaoxi", "请至少选择一项技能");
//            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
//        }
//        else{
//            //将前端注册界面的注册信息写入UserClass类的对象user中
//            UserClass user =new UserClass();
//            user.setUser_id(usrnum);
//            user.setUser_name(username);
//            user.setPassward(pwd);
//            user.setAdmin(radio);
//            user.setQuestion(question);
//            user.setAnswer(answer);
//            String strCheck="";
//            if(checkbox1.equals("on")){
//                strCheck+="矢量分析;";
//            }
//            if(checkbox2.equals("on")){
//                strCheck+="栅格分析;";
//            }
//            if(checkbox3.equals("on")){
//                strCheck+="地形分析;";
//            }
//            if(checkbox4.equals("on")){
//                strCheck+="网络分析;";
//            }
//            if(checkbox5.equals("on")){
//                strCheck+="遥感处理;";
//            }
//            user.setSkill(strCheck);
//
//            String str=Register.getValue(user);//调用Register.getValue将user的信息写入到数据库中
//
//            if(str=="成功"){
//                response.sendRedirect("sign_in.html");
//            }
//            else{
//                request.setAttribute("xiaoxi", str); //向request域中放置信息
//                request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);//转发到成功页面
//            }
//        }




    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
