package test.dao;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;
import test.ThreadPool.ThreadPoolDemo;
import test.controller.*;
import test.factor.UploadFactory;
import test.vo.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static com.sun.deploy.trace.Trace.print;

/**
 * Created by hao123 on 2019/9/18.
 */
public class Agent3 {


    //改变agengt状态
    public static void change_a3_stateAs(String state) throws SQLException {
        Connection conn = DatabaseConn.getConn();// 使用DatabaseConn类来的getConn方法来建立mysql数据库,zs 建立数据库连接对象
        Statement stmt = conn.createStatement();
        String sqlusedb = "use cloud";
        int result1 = stmt.executeUpdate(sqlusedb);
        String sql = "UPDATE agent SET state="+state+" where agentID='a3'";
        stmt.executeUpdate(sql);
        conn.close();
    }
    //验证登录，成功后初始化
    public static void agentLogin_in(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        change_a3_stateAs("1");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("userid"); //后端获取登陆界面sign_in.html的用户账号，密码，以及用户类型的值
        String pwd = request.getParameter("password");
        String radio=request.getParameter("radioname");
        //判断账号密码是否填完整
        if(id==null||id==""){
            request.setAttribute("xiaoxi", "账号为空");
            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
        }else if(pwd==null||pwd==""){
            request.setAttribute("xiaoxi", "密码为空");
            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
        }else if(radio==null||radio==""){
            request.setAttribute("xiaoxi", "用户类型为空");
            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
        }else{
            if(Login.getValue(id, pwd,radio)){//使用Login.getValue方法验证登录账号，密码，验证成功后；进行下面工作：
//                User_agent useragent=new User_agent(id.toString());
                //将User对象的其它字段进行初始化
                User.setLocality("");
                User.setType("");
                User.setCxtime("");
                User.setWn_coord("");
                User.setEs_coord("");
                User.setFriend("");
                FriendClass.setFriendid("");
                FriendClass.setFriendname("");
                User.setRw_name("");
                User.setRwskill("");
                User.setPay("");
                User.setUrl("");
                User.setDescri("");
                User.setSj_id("");
                response.sendRedirect("search.jsp");
            }else{
                response.sendRedirect("sign_in.html"); //重定向到首页
            }
        }
        change_a3_stateAs("0");
    }



    //用户注册
    public static void agentRegister(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        change_a3_stateAs("1");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String usrnum = request.getParameter("usrnum"); //得到jsp页面传过来的参数
        String username = request.getParameter("username");
        String pwd = request.getParameter("usrpwd");
        String radio=request.getParameter("select-type");
        String question = request.getParameter("select-question");
        String answer=request.getParameter("answer");
        String checkbox1=request.getParameter("checkbox1");
        String checkbox2=request.getParameter("checkbox2");
        String checkbox3=request.getParameter("checkbox3");
        String checkbox4=request.getParameter("checkbox4");
        String checkbox5=request.getParameter("checkbox5");
        if(usrnum==null||usrnum==""){
            request.setAttribute("xiaoxi", "账号为空");
            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
        }else if(username==null||username==""){
            request.setAttribute("xiaoxi", "用户名为空");
            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
        }else if(pwd==null||pwd==""){
            request.setAttribute("xiaoxi", "密码为空");
            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
        }else if(radio==null||radio==""){
            request.setAttribute("xiaoxi", "用户类型为空");
            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
        }else if(question==null||question==""){
            request.setAttribute("xiaoxi", "密保问题为空");
            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
        }else if(answer==null||answer==""){
            request.setAttribute("xiaoxi", "密保答案为空");
            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
        }
        else if(checkbox1=="" && checkbox2=="" && checkbox3=="" && checkbox4=="" && checkbox5==""){
            request.setAttribute("xiaoxi", "请至少选择一项技能");
            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
        }
        else{
            //将前端注册界面的注册信息写入UserClass类的对象user中
            UserClass user =new UserClass();
            user.setUser_id(usrnum);
            user.setUser_name(username);
            user.setPassward(pwd);
            user.setAdmin(radio);
            user.setQuestion(question);
            user.setAnswer(answer);
            String strCheck="";
            if(checkbox1.equals("on")){
                strCheck+="矢量分析;";
            }
            if(checkbox2.equals("on")){
                strCheck+="栅格分析;";
            }
            if(checkbox3.equals("on")){
                strCheck+="地形分析;";
            }
            if(checkbox4.equals("on")){
                strCheck+="网络分析;";
            }
            if(checkbox5.equals("on")){
                strCheck+="遥感处理;";
            }
            user.setSkill(strCheck);

            String str=Register.getValue(user);//调用Register.getValue将user的信息写入到数据库中

            if(str=="成功"){
                response.sendRedirect("sign_in.html");
            }
            else{
                request.setAttribute("xiaoxi", str); //向request域中放置信息
                request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);//转发到成功页面
            }
        }
        change_a3_stateAs("0");
    }



    //找回密码
    public static void agentFindPwd(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        change_a3_stateAs("1");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String usrnum = request.getParameter("usrnum"); //得到jsp页面传过来的参数
        String question = request.getParameter("select-question");
        String answer=request.getParameter("answer");
        //判断注册界面要输入的文本框内容是否完整，否则弹报错信息
        if(usrnum==null||usrnum==""){
            request.setAttribute("xiaoxi", "账号为空");
            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
        }else if(question==null||question==""){
            request.setAttribute("xiaoxi", "密保问题为空");
            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
        }else if(answer==null||answer==""){
            request.setAttribute("xiaoxi", "密保答案为空");
            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
        }else{
            String pwd= PwdProtection.getValue(usrnum,question,answer);//使用PwdProtection.getValue方法找回密码
            if(pwd==""){
                request.setAttribute("xiaoxi", "密码找回失败，请检验密保答案是否有误");
                request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
            }
            else{
                request.setAttribute("xiaoxi", pwd);
                request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
            }
        }
        change_a3_stateAs("0");
    }



    //上传数据信息到MySQL&上传数据文件到hsdfs
    public static void agent_UploadDatainfoAndfile(HttpServletRequest request, HttpServletResponse response,Logger logger,
                                                   Properties properties,Properties hadoopProps) throws SQLException, UnsupportedEncodingException {

        change_a3_stateAs("1");
        request.setCharacterEncoding("UTF-8");
        String filePath = properties.getProperty("path");
        logger.info("source file path:" + filePath + "" );
        //创建一个新的文件上传处理程序
        ServletFileUpload upload = new UploadFactory().getUpload(properties,"tmpFilePath");
        try{
            //解析获取的文件
            List<FileItem> fileItems = upload.parseRequest(request);
            //处理上传的文件
            Iterator i = fileItems.iterator();
            logger.info("begin to upload file to tomcat server</p>");
            FilePack filePack=new FilePack();
            //使用线程池
            ThreadPoolDemo poolDemo = new ThreadPoolDemo();
            //使用线程池起多个线程执行相同任务
            response.getWriter().println(poolDemo.threadPoolDemo1(i,hadoopProps,filePath,filePack));//上传文件到云hadfs，两步走
            String fileName=filePack.fileclass.FileName;
            String fileSize =filePack.fileclass.Filesize;
            String dataName= filePack.fileclass.DataName;
            logger.error("文件名:"+fileName);
            logger.error("文件大小:"+fileSize);
            logger.error("文件正名:"+dataName);
            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String  sqlusedb="use cloud";
            logger.error("连接数据库 :  "+sqlusedb);
            int result1 = stmt.executeUpdate(sqlusedb);
            if(result1 == -1)
                logger.error("连接数据库错误");
            String sql = "insert into sjb(sj_name,locality,size,type,user_id,cxtime,rd,dataname,wn_coordinate,es_coordinate) value('"+ fileName+"','"+
                    User.getLocality()+"','"+fileSize+"','"+User.getType()+"','"+User.getUser_id()+"',"+User.getCxtime()+",0,'"+dataName+"','"+User.getWn_coord()
                    +"','"+User.getEs_coord()+"')";
            logger.error("连接数据库 :  "+sql);
            int result = stmt.executeUpdate(sql);
            if(result != -1){
                sql = "update user set scl=scl+1 where user_id='"+User.getUser_id()+"'";
                result = stmt.executeUpdate(sql);
            }else{
                logger.error("导入数据错误 :  ");
            }
            conn.close();
        }catch(Exception e){
            logger.error("处理上传文件时出现错误",e);
        }
        change_a3_stateAs("0");
    }



    //名称检索
    public static void agentNameSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        change_a3_stateAs("1");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String name=request.getParameter("dataname");
        List<DataClass> userAll= Search.getValue(name);//根据数据名称搜索，将结果存入到userAll中
        request.setAttribute("userAll",userAll);
        request.getRequestDispatcher("/download.jsp").forward(request, response);
        change_a3_stateAs("0");
    }
    //高级检索
    public static void agentSuperSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        change_a3_stateAs("1");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String locality=request.getParameter("area");
        String cxtime=request.getParameter("year");
        String type=request.getParameter("datatype");
        String str="";
        if(locality==""&&cxtime==""&&type==""){
            request.setAttribute("xiaoxi", "各项查询信息皆为空，请至少输入一项信息");
            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
        }
        else{
            if(locality!=""){
                str+="locality like '%"+locality+"%' and";
            }
            if(cxtime!=""){
                str+=" cxtime >="+cxtime+"0101 and cxtime <="+cxtime+"1231 and";
            }
            if(type!=""){
                str+=" type='"+type+"'";
            }
        }
        List<DataClass> userAll= Search.getAdvValue(str);
        request.setAttribute("userAll",userAll);
        request.getRequestDispatcher("/download.jsp").forward(request, response);
        change_a3_stateAs("0");
    }
    //矩形框检索
    public static void agentRangeSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        change_a3_stateAs("1");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String wn_coord=request.getParameter("westnorth");
        String es_coord=request.getParameter("eastsouth");
        String str="";
        if(wn_coord!=""&&es_coord!=""){

            List<DataClass> userAll= Search.getRangeValue(wn_coord,es_coord);
            request.setAttribute("userAll",userAll);
            request.getRequestDispatcher("/download.jsp").forward(request, response);
        }
        else{
            request.setAttribute("xiaoxi", "各项查询范围为空，请输入范围信息");
            request.getRequestDispatcher("/xiaoxi.jsp").forward(request, response);
        }
        change_a3_stateAs("0");
    }


    //下载数据
    public static void agentDownload(HttpServletRequest request, HttpServletResponse response,
                                     Logger logger,Properties localProps,Properties hadoopProps) throws IOException, SQLException {
        change_a3_stateAs("1");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //String borrowName=request.getParameter("filename");
        String id = request.getParameter("sj_id");
        String receiver = request.getParameter("receiver");
        String name= Download.getValue(id,receiver);
        String hadooppath = hadoopProps.getProperty("hdfsPath");
        logger.error("path is :  " + hadooppath);
        String fileName = String.valueOf(name);
        logger.error("fileName is :  " + fileName);
        String hdfspath= hadoopProps.getProperty("hdfsPath")+ "/" + fileName;
        logger.error("hdfspath is :  " + hdfspath);
        String localpath = localProps.getProperty("localPath");
        logger.error("localpath is :  " + localpath);
        Configuration conf = new Configuration();
        HdfsToLocal.copyFile(hadooppath ,localpath,hdfspath,conf);
        String downloadPath=localProps.getProperty("localPath");
        DataInputStream in = null;
        OutputStream out = null;
        try{
            response.reset();// 清空输出流
            fileName = URLEncoder.encode(fileName,"UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);// 设定输出文件头
            //response.setContentType("text/plain");// 定义输出类型
            //输入流：本地文件路径
            in = new DataInputStream(new FileInputStream(new File(downloadPath +"/" + fileName)));
            //输出流
            out = response.getOutputStream();
            //输出文件
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
        } catch(Exception e){
            logger.error("输出文件错误",e);

        }
        change_a3_stateAs("0");
    }


    //发布任务
    public static void agentFabuTask(HttpServletRequest request, HttpServletResponse response,Logger logger,
                                     Properties properties,Properties hadoopProps) throws SQLException, UnsupportedEncodingException {
        change_a3_stateAs("1");
        request.setCharacterEncoding("UTF-8");
        String filePath = properties.getProperty("path");
        logger.info("source file path:" + filePath + "" );
        //创建一个新的文件上传处理程序
        ServletFileUpload upload = new UploadFactory().getUpload(properties,"tmpFilePath");
        try{
            //解析获取的文件
            List<FileItem> fileItems = upload.parseRequest(request);
            //处理上传的文件
            Iterator i = fileItems.iterator();
            logger.info("begin to upload file to tomcat server</p>");
            FilePack filePack=new FilePack();
            //使用线程池
            ThreadPoolDemo poolDemo = new ThreadPoolDemo();
            //使用线程池起多个线程执行相同任务
            response.getWriter().println(poolDemo.threadPoolDemo1(i,hadoopProps,filePath,filePack));
            String fileName=filePack.fileclass.FileName;
            String fileSize =filePack.fileclass.Filesize;
            String dataName= filePack.fileclass.DataName;
            logger.error("文件名:"+fileName);
            logger.error("文件大小:"+fileSize);
            logger.error("文件正名:"+dataName);
            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String  sqlusedb="use cloud";
            logger.error("连接数据库 :  "+sqlusedb);
            int result1 = stmt.executeUpdate(sqlusedb);
            if(result1 == -1)
                logger.error("连接数据库错误");
            String sql = "insert into sjb(sj_name,locality,size,type,user_id,cxtime,rd,dataname,wn_coordinate,es_coordinate) value('"+ fileName+"','"+
                    User.getLocality()+"','"+fileSize+"','"+User.getType()+"','"+User.getUser_id()+"',"+User.getCxtime()+",0,'"+dataName+"','"+User.getWn_coord()
                    +"','"+User.getEs_coord()+"')";
            logger.error("连接数据库 :  "+sql);
            int result = stmt.executeUpdate(sql);

            if(result != -1){
                sql = "update user set scl=scl+1 where user_id='"+User.getUser_id()+"'";
                result = stmt.executeUpdate(sql);
            }else{
                logger.error("导入数据错误 :  ");
            }

            sql="select sj_id from sjb where dataname='"+dataName+"'";
            ResultSet rs = stmt.executeQuery(sql);
            String rw_id="";
            while(rs.next()) {
                rw_id=rs.getString("sj_id");
            }
            sql="insert into rwb(rw_id,user_id,rw_name,rwskill,pay,url,descri,rwzt,rwlx) value("+rw_id+",'"+User.getUser_id()+"','"+User.getRw_name()+
                    "','"+User.getRwskill()+"','"+User.getPay()+"','"+User.getUrl()+"','"+User.getDescri()+"',0,0)";
            logger.error("连接数据库 :  "+sql);
            result = stmt.executeUpdate(sql);
            conn.close();

        }catch(Exception e){
            logger.error("处理上传文件时出现错误",e);
        }
        change_a3_stateAs("0");
    }
    //领取任务
    public static void agentLingquTask(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        change_a3_stateAs("1");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String sj_id=request.getParameter("sj_id");
        Task.Lingqu(sj_id);
        request.getRequestDispatcher("/GIS.jsp").forward(request, response);
        change_a3_stateAs("0");
    }
    //评价任务
    public static void agentPingjiaTask(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        change_a3_stateAs("1");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String rw_id=request.getParameter("sj_id");
        String receiver=request.getParameter("receiver");
        String dafen=request.getParameter("dafen");
        Task.Pingjia(rw_id,receiver,dafen);
        request.getRequestDispatcher("/GIS.jsp").forward(request, response);


        change_a3_stateAs("0");
    }
    //申请任务
    public static void agentShenqingTask(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        change_a3_stateAs("1");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String sj_id=request.getParameter("sj_id");
        Task.Shenqing(sj_id);
        request.getRequestDispatcher("/GIS.jsp").forward(request, response);
        change_a3_stateAs("0");
    }
    //提交任务
    public static void agentTijiaoTask(HttpServletRequest request, HttpServletResponse response,Logger logger,
                                       Properties properties,Properties hadoopProps) throws SQLException, UnsupportedEncodingException {
        change_a3_stateAs("1");
        request.setCharacterEncoding("UTF-8");
        String filePath = properties.getProperty("path");
        logger.info("source file path:" + filePath + "" );

        //创建一个新的文件上传处理程序
        ServletFileUpload upload = new UploadFactory().getUpload(properties,"tmpFilePath");

        try{
            //解析获取的文件
            List<FileItem> fileItems = upload.parseRequest(request);
            //处理上传的文件
            Iterator i = fileItems.iterator();
            logger.info("begin to upload file to tomcat server</p>");


            FilePack filePack=new FilePack();
            //使用线程池
            ThreadPoolDemo poolDemo = new ThreadPoolDemo();
            //使用线程池起多个线程执行相同任务
            response.getWriter().println(poolDemo.threadPoolDemo1(i,hadoopProps,filePath,filePack));

            String fileName=filePack.fileclass.FileName;
            String fileSize =filePack.fileclass.Filesize;
            String dataName= filePack.fileclass.DataName;
            logger.error("文件名:"+fileName);
            logger.error("文件大小:"+fileSize);
            logger.error("文件正名:"+dataName);

            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String  sqlusedb="use cloud";
            logger.error("连接数据库 :  "+sqlusedb);

            int result1 = stmt.executeUpdate(sqlusedb);
            if(result1 == -1)
                logger.error("连接数据库错误");
            String sql="select * from sjb where sj_id="+User.getSj_id();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                User.setLocality(rs.getString("locality"));
                User.setType(rs.getString("type"));
                String time=rs.getString("cxtime");
                time=time.substring(0,10);
                time=time.replace("-","");
                User.setCxtime(time);
                User.setWn_coord(rs.getString("wn_coordinate"));
                User.setEs_coord(rs.getString("es_coordinate"));
            }
            sql = "insert into sjb(sj_name,locality,size,type,user_id,cxtime,rd,dataname,wn_coordinate,es_coordinate) value('"+ fileName+"','"+
                    User.getLocality()+"','"+fileSize+"','"+User.getType()+"','"+User.getUser_id()+"',"+User.getCxtime()+",0,'"+dataName+"','"+User.getWn_coord()
                    +"','"+User.getEs_coord()+"')";
            logger.error("连接数据库 :  "+sql);
            int result = stmt.executeUpdate(sql);

            if(result != -1){
                sql = "update user set scl=scl+1,rwwcl=rwwcl+1,rwdql=rwdql-1 where user_id='"+User.getUser_id()+"'";
                result = stmt.executeUpdate(sql);
            }else{
                logger.error("导入数据错误 :  ");
            }
            sql="select sj_id from sjb where dataname='"+dataName+"'";
            rs = stmt.executeQuery(sql);
            String sj_id="";
            while(rs.next()) {
                sj_id=rs.getString("sj_id");
            }
            sql = "update rwb set rwzt=3, url='"+User.getUrl()+"',link="+sj_id+" where rw_id="+User.getSj_id();
            result = stmt.executeUpdate(sql);
            conn.close();
        }catch(Exception e){
            logger.error("处理上传文件时出现错误",e);
        }
        change_a3_stateAs("0");
    }
    //指派任务
    public static void agentZhipaiTask(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        change_a3_stateAs("1");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String user_id=request.getParameter("user_id");
        Task.Zhipai(user_id);
        request.getRequestDispatcher("/GIS.jsp").forward(request, response);
        change_a3_stateAs("0");
    }

}
