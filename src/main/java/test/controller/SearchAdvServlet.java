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
 * Created by lai on 2019/4/24.
 */
@WebServlet(name = "SearchAdvServlet",urlPatterns = {"/SearchAdvServlet"})
public class SearchAdvServlet extends HttpServlet {
    Logger logger = Logger.getLogger(getClass());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


//        try {
//            agentCenter.Adjust_agent("agentSuperSearch",request,response,logger);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        try {
            Agent_baseFunction agent_baseFunction=new Agent_baseFunction();
            agent_baseFunction.agentSuperSearch(request,response);//调用agent进行高级检索
           // Agent1.agentSuperSearch(request,response);//调用agent进行高级检索
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        String locality=request.getParameter("area");
//        String cxtime=request.getParameter("year");
//        String type=request.getParameter("datatype");
//        String str="";
//        if(locality==""&&cxtime==""&&type==""){
//            request.setAttribute("xiaoxi", "各项查询信息皆为空，请至少输入一项信息");
//            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
//        }
//        else{
//            if(locality!=""){
//                str+="locality like '%"+locality+"%' and";
//            }
//            if(cxtime!=""){
//                str+=" cxtime >="+cxtime+"0101 and cxtime <="+cxtime+"1231 and";
//            }
//            if(type!=""){
//                str+=" type='"+type+"'";
//            }
//        }
//       List<DataClass> userAll= Search.getAdvValue(str);
//        request.setAttribute("userAll",userAll);
//        request.getRequestDispatcher("/download.jsp").forward(request, response);
    }
}
