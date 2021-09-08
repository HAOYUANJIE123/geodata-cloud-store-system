package test.dao;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by lai on 2019/4/17.
 */
public class PwdProtection {
    private static Logger logger = Logger.getLogger(test.dao.PwdProtection.class);

    public static String getValue(String id,String question,String answer) throws IOException {


        try {
            boolean flag=false;
            String str="";
            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);

            String sql = "select * from user where user_id='"+id+"' and question='"+question+"' and answer='"+answer+"'";
            logger.error("sql语句错误"+ sql);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                if (rs.getString("user_id").equals(id) && rs.getString("question").equals(question)&& rs.getString("answer").equals(answer)) {
                    str = rs.getString("password");
                }
            }
            conn.close();
            return str;

        }catch (SQLException e) {
            logger.error("MySQL操作错误"+ e);
            return "";
        }catch (Exception e){
            logger.error("密码找回时出现错误"+ e);
            return "";
        }
    }
}
