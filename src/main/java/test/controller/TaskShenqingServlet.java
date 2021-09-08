package test.controller;

import org.apache.log4j.Logger;
import test.dao.Agent1;
import test.dao.Agent_baseFunction;
import test.dao.Task;
import test.dao.agentCenter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by lai on 2019/5/22.
 */
@WebServlet(name = "TaskShenqingServlet",urlPatterns = {"/TaskShenqingServlet"})
public class TaskShenqingServlet extends HttpServlet {
    Logger logger = Logger.getLogger(getClass());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        try {
//            agentCenter.Adjust_agent("agentShenqingTask",request,response,logger);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
            //Agent1.agentShenqingTask(request,response);//调用agent申请任务
            Agent_baseFunction agent_baseFunction=new Agent_baseFunction();
            agent_baseFunction.agentShenqingTask(request,response);//调用agent申请任务
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        String sj_id=request.getParameter("sj_id");
//        Task.Shenqing(sj_id);
//        request.getRequestDispatcher("/GIS.jsp").forward(request, response);
    }
}
