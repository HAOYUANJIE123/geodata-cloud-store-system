package test.controller;

import org.apache.log4j.Logger;
import test.dao.Agent1;
import test.dao.Agent_baseFunction;
import test.dao.Task;
import test.dao.agentCenter;
import test.vo.User;
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
 * Created by lai on 2019/5/21.
 */
@WebServlet(name = "TaskZhipaiServlet",urlPatterns = {"/TaskZhipaiServlet"})
public class TaskZhipaiServlet extends HttpServlet {
    Logger logger = Logger.getLogger(getClass());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        try {
//            agentCenter.Adjust_agent("agentZhipaiTask",request,response,logger);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
         //   Agent1.agentZhipaiTask(request,response);//调用agent指派任务
            Agent_baseFunction agent_baseFunction=new Agent_baseFunction();
            agent_baseFunction.agentZhipaiTask(request,response);//调用agent指派任务
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        String user_id=request.getParameter("user_id");
//        Task.Zhipai(user_id);
//        request.getRequestDispatcher("/GIS.jsp").forward(request, response);
    }
}
