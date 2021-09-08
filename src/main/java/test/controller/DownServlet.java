package test.controller;

import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;
import test.dao.*;
import test.factor.GsonFactory;
import test.factor.ReadPropertiesFactory;
import test.vo.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by lai on 2019/1/17.
 */
@WebServlet(name = "DownServlet",urlPatterns = {"/DownServlet"})
public class DownServlet extends HttpServlet {
    Properties localProps = new ReadPropertiesFactory().getDruidProperties();
    Properties hadoopProps = new ReadPropertiesFactory().getHadoopProperties();
    Logger logger = Logger.getLogger(getClass());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        try {
//            agentCenter.Adjust_agent("agentDownload",request,response,logger);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
      //      Agent1.agentDownload(request,response,logger,localProps,hadoopProps);//调用agent方法下载数据
            Agent_baseFunction agent_baseFunction=new Agent_baseFunction();
            agent_baseFunction.agentDownload(request,response,logger,localProps,hadoopProps);//调用agent方法下载数据
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        //String borrowName=request.getParameter("filename");
//        String id = request.getParameter("sj_id");
//        String receiver = request.getParameter("receiver");
//        String name= Download.getValue(id,receiver);
//
//        String hadooppath = hadoopProps.getProperty("hdfsPath");
//        logger.error("path is :  " + hadooppath);
//        String fileName = String.valueOf(name);
//        logger.error("fileName is :  " + fileName);
//        String hdfspath= hadoopProps.getProperty("hdfsPath")+ "/" + fileName;
//        logger.error("hdfspath is :  " + hdfspath);
//        String localpath = localProps.getProperty("localPath");
//        logger.error("localpath is :  " + localpath);
//        Configuration conf = new Configuration();
//        HdfsToLocal.copyFile(hadooppath ,localpath,hdfspath,conf);
//        String downloadPath=localProps.getProperty("localPath");
//        DataInputStream in = null;
//        OutputStream out = null;
//        try{
//            response.reset();// 清空输出流
//            fileName = URLEncoder.encode(fileName,"UTF-8");
//            response.setCharacterEncoding("UTF-8");
//            response.setHeader("Content-disposition", "attachment; filename=" + fileName);// 设定输出文件头
//            //response.setContentType("text/plain");// 定义输出类型
//            //输入流：本地文件路径
//            in = new DataInputStream(new FileInputStream(new File(downloadPath +"/" + fileName)));
//            //输出流
//            out = response.getOutputStream();
//            //输出文件
//            int bytes = 0;
//            byte[] bufferOut = new byte[1024];
//            while ((bytes = in.read(bufferOut)) != -1) {
//                out.write(bufferOut, 0, bytes);
//            }
//        } catch(Exception e){
//            logger.error("输出文件错误",e);
//
//        }
    }
}
