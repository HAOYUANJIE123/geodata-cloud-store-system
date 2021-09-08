package test.controller;

import org.apache.log4j.Logger;
import test.dao.Agent1;
import test.dao.Agent_baseFunction;
import test.dao.Search;
import test.dao.agentCenter;
import test.vo.DataClass;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by lai on 2019/4/22.
 */
@WebServlet(name = "SearchServlet",urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    Logger logger = Logger.getLogger(getClass());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        try {
//            agentCenter.Adjust_agent("agentNameSearch",request,response,logger);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        try {
          //  Agent1.agentNameSearch(request,response);//调用Agengt进行名称检索
            Agent_baseFunction agent_baseFunction=new Agent_baseFunction();
            agent_baseFunction.agentNameSearch(request,response);//调用Agengt进行名称检索
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        String name=request.getParameter("dataname");
//       List<DataClass> userAll= Search.getValue(name);//根据数据名称搜索，将结果存入到userAll中
//        request.setAttribute("userAll",userAll);
//        request.getRequestDispatcher("/download.jsp").forward(request, response);


    }
}
