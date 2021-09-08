package test.service;

import org.apache.log4j.Logger;
import test.dao.DatabaseConn;
import test.vo.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lai on 2019/5/10.
 */
public class GetFriendInfo {

    private static Logger logger = Logger.getLogger(test.service.GetFriendInfo.class);
    public static List<String> getFileList() throws IOException {

        try{
            List<String> friName = new ArrayList<>();

//            friName.add("user_1");
//            friName.add("user_2");
//            friName.add("user_3");

            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String  sqlusedb="use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);

            String sql = "select user_2,username_2 from gxb where user_1='"+ User.getUser_id()+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                friName.add(rs.getString("user_2")+"("+ rs.getString("username_2")+")");
            }

            sql = "select distinct user_1,username_1 from gxb where user_2='"+ User.getUser_id()+"'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                friName.add(rs.getString("user_1")+"("+ rs.getString("username_1")+")");
                logger.error("用户："+ rs.getString("user_1")+"("+ rs.getString("username_1")+")");
            }
            conn.close();

            return friName;
        }catch (Exception e){
            logger.error("获取好友名字出现错误"+ e);
            return null;
        }
    }
}
