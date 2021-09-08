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
 * Created by lai on 2019/4/25.
 */
@WebServlet(name = "SearchRangeServlet",urlPatterns = {"/SearchRangeServlet"})
public class SearchRangeServlet extends HttpServlet {
    Logger logger = Logger.getLogger(getClass());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        try {
//            agentCenter.Adjust_agent("agentRangeSearch",request,response,logger);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        try {
            Agent_baseFunction agent_baseFunction=new Agent_baseFunction();
            agent_baseFunction.agentRangeSearch(request,response);//调用agent进行矩形框检索
           // Agent1.agentRangeSearch(request,response);//调用agent进行矩形框检索
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        String wn_coord=request.getParameter("westnorth");
//        String es_coord=request.getParameter("eastsouth");
//        String str="";
//        if(wn_coord!=""&&es_coord!=""){
//
//            List<DataClass> userAll= Search.getRangeValue(wn_coord,es_coord);
//            request.setAttribute("userAll",userAll);
//            request.getRequestDispatcher("/download.jsp").forward(request, response);
//        }
//        else{
//            request.setAttribute("xiaoxi", "各项查询范围为空，请输入范围信息");
//            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
//        }
    }
}
