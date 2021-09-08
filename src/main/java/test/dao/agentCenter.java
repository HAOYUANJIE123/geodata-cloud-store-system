package test.dao;
import org.apache.log4j.Logger;
import test.factor.ReadPropertiesFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static java.lang.System.out;

/**
 * Created by hao123 on 2019/9/20.
 */
public class agentCenter {


    public static void Alert_AgentBusy( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.print("<script>alert('Agent代理占用中,请稍后尝试!');</script>");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public static void Alert_AgentState( HttpServletRequest request, HttpServletResponse response,String a) throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        PrintWriter out;
//        try {
//            out = response.getWriter();
//            out.print("<script>alert('"+a+"');</script>");
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    //检查agengt状态
    public static String check_agent_site(String agent_name) throws SQLException {
        Connection conn = DatabaseConn.getConn();// 使用DatabaseConn类来的getConn方法来建立mysql数据库,zs 建立数据库连接对象
        Statement stmt = conn.createStatement();
        String sqlusedb = "use cloud";
        int result1 = stmt.executeUpdate(sqlusedb);
        String sql = "select state from agent where agentID='"+agent_name+"'";
        ResultSet rs = stmt.executeQuery(sql);
        String result="";
        while(rs.next()){
            result=rs.getString("state").toString();
        }
        conn.close();
        return result;
    }


    public static void  Adjust_agent(String method, HttpServletRequest request, HttpServletResponse response
    ,Logger logger) throws SQLException, IOException, ServletException {

        Properties localProps = new ReadPropertiesFactory().getDruidProperties();
        Properties hadoopProps = new ReadPropertiesFactory().getHadoopProperties();
        Properties properties = new ReadPropertiesFactory().getDruidProperties();//将配置文件file.properties赋给properties

        switch(method){

            case "agentLogin_in":
                if(check_agent_site("a1").equals("0"))
                    Agent1.agentLogin_in( request,response);
                else if (check_agent_site("a2").equals("0"))
                    Agent2.agentLogin_in( request,response);
                else if (check_agent_site("a3").equals("0"))
                    Agent3.agentLogin_in( request,response);
                else Alert_AgentBusy(request,response);
                break;

            case  "agentRegister":
                if(check_agent_site("a1").equals("0"))
                    Agent1.agentRegister( request,response);
                else if (check_agent_site("a2").equals("0"))
                    Agent2.agentRegister( request,response);
                else if (check_agent_site("a3").equals("0"))
                    Agent3.agentRegister( request,response);
                else Alert_AgentBusy(request,response);
                break;

            case  "agentFindPwdr":
                if(check_agent_site("a1").equals("0"))
                    Agent1.agentFindPwd( request,response);
                else if (check_agent_site("a2").equals("0"))
                    Agent2.agentFindPwd( request,response);
                else if (check_agent_site("a3").equals("0"))
                    Agent3.agentFindPwd( request,response);
                else Alert_AgentBusy(request,response);
                break;

            case  "agent_UploadDatainfoAndfile":
                if(check_agent_site("a1").equals("0"))
                    Agent1.agent_UploadDatainfoAndfile( request,  response, logger, properties, hadoopProps);
                else if (check_agent_site("a2").equals("0"))
                    Agent2.agent_UploadDatainfoAndfile( request,  response, logger, properties, hadoopProps);
                else if (check_agent_site("a3").equals("0"))
                    Agent3.agent_UploadDatainfoAndfile( request,  response, logger, properties, hadoopProps);
                else Alert_AgentBusy(request,response);
                break;

            case  "agentNameSearch":
                if(check_agent_site("a1").equals("0"))
                    Agent1.agentNameSearch( request,response);
                else if (check_agent_site("a2").equals("0"))
                    Agent2.agentNameSearch( request,response);
                else if (check_agent_site("a3").equals("0"))
                    Agent3.agentNameSearch( request,response);
                else Alert_AgentBusy(request,response);
                break;

            case  "agentSuperSearch":
                if(check_agent_site("a1").equals("0"))
                    Agent1.agentSuperSearch( request,response);
                else if (check_agent_site("a2").equals("0"))
                    Agent2.agentSuperSearch( request,response);
                else if (check_agent_site("a3").equals("0"))
                    Agent3.agentSuperSearch( request,response);
                else Alert_AgentBusy(request,response);
                break;

            case  "agentRangeSearch":
                if(check_agent_site("a1").equals("0"))
                    Agent1.agentRangeSearch( request,response);
                else if (check_agent_site("a2").equals("0"))
                    Agent2.agentRangeSearch( request,response);
                else if (check_agent_site("a3").equals("0"))
                    Agent3.agentRangeSearch( request,response);
                else Alert_AgentBusy(request,response);
                break;

            case  "agentDownload":
                if(check_agent_site("a1").equals("0"))
                    Agent1.agentDownload( request,  response, logger, localProps, hadoopProps);
                else if (check_agent_site("a2").equals("0"))
                    Agent2.agentDownload( request,  response, logger, localProps, hadoopProps);
                else if (check_agent_site("a3").equals("0"))
                    Agent3.agentDownload( request,  response, logger, localProps, hadoopProps);
                else Alert_AgentBusy(request,response);
                break;

            case  "agentFabuTask":
                if(check_agent_site("a1").equals("0"))
                    Agent1.agentFabuTask( request,  response, logger, properties, hadoopProps);
                else if (check_agent_site("a2").equals("0"))
                    Agent2.agentFabuTask( request,  response, logger, properties, hadoopProps);
                else if (check_agent_site("a3").equals("0"))
                    Agent3.agentFabuTask( request,  response, logger, properties, hadoopProps);
                else Alert_AgentBusy(request,response);
                break;

            case  "agentLingquTask":
                if(check_agent_site("a1").equals("0"))
                    Agent1.agentLingquTask( request,response);
                else if (check_agent_site("a2").equals("0"))
                    Agent2.agentLingquTask( request,response);
                else if (check_agent_site("a3").equals("0"))
                    Agent3.agentLingquTask( request,response);
                else Alert_AgentBusy(request,response);
                break;

            case  "agentPingjiaTask":
                if(check_agent_site("a1").equals("0"))
                    Agent1.agentPingjiaTask( request,response);
                else if (check_agent_site("a2").equals("0"))
                    Agent2.agentPingjiaTask( request,response);
                else if (check_agent_site("a3").equals("0"))
                    Agent3.agentPingjiaTask( request,response);
                else Alert_AgentBusy(request,response);
                break;

            case  "agentShenqingTask":
                if(check_agent_site("a1").equals("0"))
                    Agent1.agentShenqingTask( request,response);
                else if (check_agent_site("a2").equals("0"))
                    Agent2.agentShenqingTask( request,response);
                else if (check_agent_site("a3").equals("0"))
                    Agent3.agentShenqingTask( request,response);
                else Alert_AgentBusy(request,response);
                break;

            case  "agentTijiaoTask":
                if(check_agent_site("a1").equals("0"))
                    Agent1.agentTijiaoTask( request,  response, logger, properties, hadoopProps);
                else if (check_agent_site("a2").equals("0"))
                    Agent2.agentTijiaoTask( request,  response, logger, properties, hadoopProps);
                else if (check_agent_site("a3").equals("0"))
                    Agent3.agentTijiaoTask( request,  response, logger, properties, hadoopProps);
                else Alert_AgentBusy(request,response);
                break;

            case  "agentZhipaiTask":
                if(check_agent_site("a1").equals("0"))
                    Agent1.agentZhipaiTask( request,response);
                else if (check_agent_site("a2").equals("0"))
                    Agent2.agentZhipaiTask( request,response);
                else if (check_agent_site("a3").equals("0"))
                    Agent3.agentZhipaiTask( request,response);
                else Alert_AgentBusy(request,response);
                break;



        }







    }

}
