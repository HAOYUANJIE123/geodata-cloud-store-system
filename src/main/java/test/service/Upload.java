package test.service;

import org.apache.commons.fileupload.FileItem;
import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;
import test.dao.DatabaseConn;
import test.dao.HdfsDao;
import test.vo.DataClass;
import test.vo.FilePack;
import test.vo.User;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Created by lai on 2019/1/14.
 */
public class Upload {
    private static Logger logger = Logger.getLogger(test.service.Upload.class);

    public static String Upload(FileItem fileItem, Properties hadoopProps, String filePath,String dataName) {
        String localPath="";
        String hdfsPath="";
        try{


                if (!fileItem.isFormField()){
                //将上传文件从本地上传到云服务器
                    File file;
                    file = new File(filePath,dataName);
                    fileItem.write(file);
                    logger.info("upload file to tomcat server success!");
                    logger.info("start to upload file to hadoop");
                    localPath = filePath + "/" + dataName;
                    logger.error("FileName is :  " + dataName);
                    logger.error("File path is :  " + file.getAbsolutePath());
                    logger.error("FileName of File is :" + file.getName());
                    hdfsPath = hadoopProps.getProperty("hdfsPath");
                 }

        }catch (Exception e){
            logger.error("处理上传文件时出现错误",e);
            return "upload失败";
        }



        Configuration conf = new Configuration();
        //将tomcat上的文件上传到hadoop上
        return HdfsDao.copyFile(localPath,hdfsPath,conf);//将上传文件从云服务器本地上传到hadfs关键方法


    }

    public static List<DataClass> Uploadinfo()  throws IOException {

        try {

            List<DataClass> listDC=new ArrayList<>();

            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);
            String sql = "select sj_id,sj_name,locality,size,type,cxtime,wn_coordinate,es_coordinate from sjb where user_id='"+ User.getUser_id()+"'";
            logger.error("sql语句错误"+ sql);
            ResultSet rs = stmt.executeQuery(sql);

            DataClass dc;
            while(rs.next()) {
                dc=new DataClass();
                dc.setSj_id(rs.getString("sj_id"));
                dc.setSj_name(rs.getString("sj_name"));
                dc.setLocality(rs.getString("locality"));
                dc.setSize(rs.getString("size"));
                dc.setType(rs.getString("type"));
                dc.setCxtime(rs.getString("cxtime"));
                dc.setWn_coord(rs.getString("wn_coordinate"));
                dc.setEs_coord(rs.getString("es_coordinate"));
                listDC.add(dc);
            }
            conn.close();
            return listDC;


        }catch (SQLException e) {
            logger.error("MySQL操作错误"+ e);
            return null;
        }catch (Exception e){
            logger.error("注册时出现错误"+ e);
            return null;
        }

    }
}
