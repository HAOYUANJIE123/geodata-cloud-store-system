package test.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import test.ThreadPool.ThreadPoolDemo;
import test.dao.Agent1;
import test.dao.Agent_baseFunction;
import test.dao.DatabaseConn;
import test.dao.agentCenter;
import test.factor.ReadPropertiesFactory;
import test.factor.UploadFactory;
import test.vo.FilePack;
import test.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Created by lai on 2019/5/20.
 */
@WebServlet(name = "TaskFabuServlet",urlPatterns = {"/TaskFabuServlet"})
public class TaskFabuServlet extends HttpServlet {
    Properties properties = new ReadPropertiesFactory().getDruidProperties();
    Properties hadoopProps = new ReadPropertiesFactory().getHadoopProperties();
    Logger logger = Logger.getLogger(getClass());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        try {
//            agentCenter.Adjust_agent("agentFabuTask",request,response,logger);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
           // Agent1.agentFabuTask(request,response,logger, properties, hadoopProps);//调用agent发布任务
            Agent_baseFunction agent_baseFunction=new Agent_baseFunction();
            agent_baseFunction.agentFabuTask(request,response,logger, properties, hadoopProps);//调用agent发布任务
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        request.setCharacterEncoding("UTF-8");
//        String filePath = properties.getProperty("path");
//        logger.info("source file path:" + filePath + "" );
//        //创建一个新的文件上传处理程序
//        ServletFileUpload upload = new UploadFactory().getUpload(properties,"tmpFilePath");
//        try{
//            //解析获取的文件
//            List<FileItem> fileItems = upload.parseRequest(request);
//            //处理上传的文件
//            Iterator i = fileItems.iterator();
//            logger.info("begin to upload file to tomcat server</p>");
//            FilePack filePack=new FilePack();
//            //使用线程池
//            ThreadPoolDemo poolDemo = new ThreadPoolDemo();
//            //使用线程池起多个线程执行相同任务
//            response.getWriter().println(poolDemo.threadPoolDemo1(i,hadoopProps,filePath,filePack));
//            String fileName=filePack.fileclass.FileName;
//            String fileSize =filePack.fileclass.Filesize;
//            String dataName= filePack.fileclass.DataName;
//            logger.error("文件名:"+fileName);
//            logger.error("文件大小:"+fileSize);
//            logger.error("文件正名:"+dataName);
//            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
//            Statement stmt = conn.createStatement();
//            String  sqlusedb="use cloud";
//            logger.error("连接数据库 :  "+sqlusedb);
//            int result1 = stmt.executeUpdate(sqlusedb);
//            if(result1 == -1)
//                logger.error("连接数据库错误");
//            String sql = "insert into sjb(sj_name,locality,size,type,user_id,cxtime,rd,dataname,wn_coordinate,es_coordinate) value('"+ fileName+"','"+
//                    User.getLocality()+"','"+fileSize+"','"+User.getType()+"','"+User.getUser_id()+"',"+User.getCxtime()+",0,'"+dataName+"','"+User.getWn_coord()
//                    +"','"+User.getEs_coord()+"')";
//            logger.error("连接数据库 :  "+sql);
//            int result = stmt.executeUpdate(sql);
//
//            if(result != -1){
//                sql = "update user set scl=scl+1 where user_id='"+User.getUser_id()+"'";
//                result = stmt.executeUpdate(sql);
//            }else{
//                logger.error("导入数据错误 :  ");
//            }
//
//            sql="select sj_id from sjb where dataname='"+dataName+"'";
//            ResultSet rs = stmt.executeQuery(sql);
//            String rw_id="";
//            while(rs.next()) {
//                rw_id=rs.getString("sj_id");
//            }
//            sql="insert into rwb(rw_id,user_id,rw_name,rwskill,pay,url,descri,rwzt,rwlx) value("+rw_id+",'"+User.getUser_id()+"','"+User.getRw_name()+
//                    "','"+User.getRwskill()+"','"+User.getPay()+"','"+User.getUrl()+"','"+User.getDescri()+"',0,0)";
//            logger.error("连接数据库 :  "+sql);
//            result = stmt.executeUpdate(sql);
//            conn.close();
//
//        }catch(Exception e){
//            logger.error("处理上传文件时出现错误",e);
//        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String locality=request.getParameter("locality");
        String type=request.getParameter("datatype");
        String cxtime=request.getParameter("time");
        String wn_coord=request.getParameter("westnorth");
        String es_coord=request.getParameter("eastsouth");
        String taskname=request.getParameter("taskname");
        String pay=request.getParameter("money");
        String skill=request.getParameter("skill");
        String url=request.getParameter("in-dataUrl");
        String descri=request.getParameter("taskDescribe");
        User.setType(type);
        User.setLocality(locality);
        User.setCxtime(cxtime);
        User.setWn_coord(wn_coord);
        User.setEs_coord(es_coord);
        User.setEs_coord(es_coord);
        User.setRw_name(taskname);
        User.setPay(pay);
        User.setRwskill(skill);
        User.setUrl(url);
        User.setDescri(descri);
        logger.error("技能:"+User.getRwskill());
//        logger.error("文件类型:"+User.getType());
//        logger.error("时间:"+User.getCxtime());
//        logger.error("西北:"+User.getWn_coord());
//        logger.error("东南:"+User.getEs_coord());
//        logger.error("好友:"+User.getEs_coord());
        response.sendRedirect("GIS.jsp");
    }
}
