package test.dao;

import org.apache.log4j.Logger;
import test.vo.DataClass;
import test.vo.FriendClass;
import test.vo.FriendInfoClass;
import test.vo.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lai on 2019/5/11.
 */
public class Friend {
    private static Logger logger = Logger.getLogger(test.dao.Friend.class);

    public static void getFriendName(String friend_id) throws IOException {
        try {
            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);
            String sql = "select user_name from user where user_id='" + friend_id + "'";
            logger.error("sql语句错误" + sql);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                FriendClass.setFriendid(friend_id);
                FriendClass.setFriendname(rs.getString("user_name"));
            }
            conn.close();
        }catch (SQLException e) {
            logger.error("MySQL操作错误"+ e);
        }catch (Exception e){
            logger.error("查询时出现错误"+ e);
        }
    }

    public static void requestFriend() throws IOException {
        try {

            if(FriendClass.getFriendid()!=null && FriendClass.getFriendname()!=null){

                Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
                Statement stmt = conn.createStatement();
                String sqlusedb = "use cloud";
                int result1 = stmt.executeUpdate(sqlusedb);

                String sql = "insert into gxb(user_1,username_1,user_2,username_2,gx) value" +
                        "('"+User.getUser_id()+"','"+User.getUser_name()+"','"+FriendClass.getFriendid()+"','"+FriendClass.getFriendname()+"',0)";
                logger.error("sql语句" + sql);
                int result3 = stmt.executeUpdate(sql);
                FriendClass.setFriendid(null);
                FriendClass.setFriendname(null);
                conn.close();
            }

        }catch (SQLException e) {
            logger.error("MySQL操作错误"+ e);
        }catch (Exception e){
            logger.error("查询时出现错误"+ e);
        }
    }

    public static List<FriendInfoClass> getNewFriend() throws IOException {//连接gxb即关系表，寻找当前用户的好友

        try {
            List<FriendInfoClass> listFriend=new ArrayList<>();

            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);

            FriendInfoClass fri;
            String sql = "select user_1,username_1 from gxb where user_2='"+User.getUser_id()+"' and gx = 0";
            logger.error("sql语句错误"+ sql);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                fri=new FriendInfoClass();
                fri.setFriendid(rs.getString("user_1"));
                fri.setFriendname(rs.getString("username_1"));
                listFriend.add(fri);
            }
            conn.close();
            return listFriend;

        }catch (SQLException e) {
            logger.error("MySQL操作错误"+ e);
            return null;
        }catch (Exception e){
            logger.error("查询时出现错误"+ e);
            return null;
        }
    }

    public static List<FriendInfoClass> getFriendInfo() throws IOException {

        try {
            List<FriendInfoClass> listFriend=new ArrayList<>();

            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);

            FriendInfoClass fri;
            String sql = "select user_1,username_1 from gxb where user_2='"+User.getUser_id()+"' and gx = 1";
            logger.error("sql语句错误"+ sql);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                fri=new FriendInfoClass();
                fri.setFriendid(rs.getString("user_1"));
                fri.setFriendname(rs.getString("username_1"));
                listFriend.add(fri);
            }

            sql = "select user_2,username_2 from gxb where user_1='"+User.getUser_id()+"' and gx = 1";
            logger.error("sql语句错误"+ sql);
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                fri=new FriendInfoClass();
                fri.setFriendid(rs.getString("user_2"));
                fri.setFriendname(rs.getString("username_2"));
                listFriend.add(fri);
            }
            conn.close();
            return listFriend;

        }catch (SQLException e) {
            logger.error("MySQL操作错误"+ e);
            return null;
        }catch (Exception e){
            logger.error("查询时出现错误"+ e);
            return null;
        }
    }

    public static void addFriend(String friendid) throws IOException {

        try {
            List<FriendInfoClass> listFriend=new ArrayList<>();

            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);
            String sql = "update gxb set gx = 1 where user_2='"+User.getUser_id()+"' and user_1= '"+friendid+"'";
            logger.error("sql语句错误"+ sql);
            int result3 = stmt.executeUpdate(sql);

            conn.close();

        }catch (SQLException e) {
            logger.error("MySQL操作错误"+ e);
        }catch (Exception e){
            logger.error("查询时出现错误"+ e);
        }
    }
}
