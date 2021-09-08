package test.dao;

import org.apache.log4j.Logger;
import test.vo.User;
import test.vo.UserClass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by lai on 2019/4/28.
 */
public class Download {
    private static Logger logger = Logger.getLogger(test.dao.Download.class);

    public static String getValue(String sj_id,String receiver) throws IOException {
        try {
            boolean flag=false;
            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result = stmt.executeUpdate(sqlusedb);
            String  sql="";
            ResultSet rs;
            String sjid=sj_id;
            if(receiver!=null&&receiver!=""){
                sql = "update rwb set rwzt=4 where receiver='"+receiver+"'";
                int result4 = stmt.executeUpdate(sql);
                sql="select pay,link from rwb where rw_id="+sj_id;
                rs = stmt.executeQuery(sql);
                int pay=0;
                while(rs.next()) {
                    pay=Integer.valueOf(rs.getString("pay"));
                    sjid=rs.getString("link");
                }
                sql="update user set points=points+"+pay+" where user_id='"+receiver+"'";
                int result5 = stmt.executeUpdate(sql);
                sql="update user set points=points-"+pay+" where user_id='"+User.getUser_id()+"'";
                int result6 = stmt.executeUpdate(sql);
            }
            sql = "select dataname from sjb where sj_id="+sjid+"";
            logger.error("sql语句"+ sql);
            rs = stmt.executeQuery(sql);
            String strName="";
//            String receiver="";
            while(rs.next()) {
                strName=rs.getString("dataname");
                flag = true;
            }
            logger.error("用户："+User.getUser_id());
            if(flag==true){
                sql = "update user set xzl = xzl+1 where user_id='"+ User.getUser_id()+"'";
                int result1 = stmt.executeUpdate(sql);
                sql = "update sjb set rd = rd+1  where sj_id="+sj_id;
//                logger.error("接受人："+ sql);
                int result2 = stmt.executeUpdate(sql);
                sql = "insert into lsb(sj_id,user_id,dataname) value("+sj_id+",'"+User.getUser_id()+"','"+strName+"')";
                int result3 = stmt.executeUpdate(sql);
            }
            conn.close();
            return strName;
        }catch (SQLException e) {
            logger.error("MySQL操作错误"+ e);
            return "";
        }catch (Exception e){
            logger.error("登录时出现错误"+ e);
            return "";
        }
    }

}
