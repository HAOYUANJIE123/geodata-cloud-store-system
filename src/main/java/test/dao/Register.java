package test.dao;

import org.apache.log4j.Logger;
import test.vo.UserClass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by lai on 2019/4/17.
 */
public class Register {
    private static Logger logger = Logger.getLogger(test.dao.Register.class);

    public static String getValue(UserClass user) throws IOException {

        String str ="";
        boolean flag=true;
        try {
            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);

            String sql = "select * from user where user_id='"+user.getUser_id()+"'";
            logger.error("sql语句"+ sql);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                if (rs.getString("user_id").equals(user.getUser_id())) {
                    flag = false;
                }
            }
            if(flag==true){
                sql = "insert into user(user_id,user_name,password,question,answer,admin,hyd,xzl,scl,skill,rwwcl,rwsbl,rwpjl,rwdql,score,points) " +
                        "values('"+user.getUser_id()+
                        "','"+user.getUser_name()+"','"+user.getPassward()+"','"+user.getQuestion()+"','"+user.getAnswer()+"','"+user.getAdmin()+"',0,0,0," +
                        "'"+user.getSkill()+"',0,0,0,0.5,0)";
                logger.error("sql语句"+ sql);
                int result = stmt.executeUpdate(sql);
                logger.error("结果"+result);
                if(result!=-1)
                    str="成功";
                else
                    str="注册失败，请重试";
            }
            else
                str= "该账号已存在";

            conn.close();
            return str;

        }catch (SQLException e) {
            logger.error("MySQL操作错误"+ e);
            return "注册失败，请重试";
        }catch (Exception e){
            logger.error("注册时出现错误"+ e);
            return "注册失败，请重试";
        }
    }
}
