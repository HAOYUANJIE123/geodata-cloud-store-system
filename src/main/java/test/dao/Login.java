package test.dao;

import org.apache.log4j.Logger;
import test.vo.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.System.out;
import static org.apache.hadoop.yarn.webapp.hamlet.HamletSpec.Media.print;

/**
 * Created by lai on 2019/4/16.
 */
public class Login {

    private static Logger logger = Logger.getLogger(test.dao.Login.class);

    //连接数据库的user表，判断输入的账号id和密码password是否与数据表中的一致
    public static boolean getValue(String id,String password,String admin) throws IOException {


        try {
            boolean flag=false;
            Connection conn = DatabaseConn.getConn();// 使用DatabaseConn类来的getConn方法来建立mysql数据库,zs 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);

            //String sql = "select * from user where user_id='"+id+"' and password='"+password+"' and admin='"+admin+"'";
            String sql = "select * from user where user_id='"+id+"' and password='"+password+"'";
            logger.error("sql语句错误"+ sql);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                out.print(rs.getString("user_id"));
                //判断如果一致，则将改用户的其它信息写入到User对象中
                if (rs.getString("user_id").equals(id) && rs.getString("password").equals(password)) {
                    User.setUser_id(id);
                    User.setUser_name(rs.getString("user_name"));
                    User.setUsertype(rs.getString("admin"));
                    User.setPassword(rs.getString("password"));
                    User.setZctime(rs.getString("zctime"));
                    User.setScl(rs.getString("scl"));
                    User.setXzl(rs.getString("xzl"));
                    User.setRwpay(rs.getString("rwpay"));
                    User.setSkill1("");
                    User.setSkill2("");
                    User.setSkill3("");
                    User.setSkill4("");
                    User.setSkill5("");

                    //基于skill的内容中的;分号将skill内容进行划分，并赋给Skill1-Skill5
                    String skill=rs.getString("skill");
                    if (skill.indexOf(";")>0){
                        User.setSkill1(skill.substring(0,skill.indexOf(";")));
                        skill=skill.substring(skill.indexOf(";")+1);
                    }
                    if (skill.indexOf(";")>0){
                        User.setSkill2(skill.substring(0,skill.indexOf(";")));
                        skill=skill.substring(skill.indexOf(";")+1);
                    }
                    if (skill.indexOf(";")>0){
                        User.setSkill3(skill.substring(0,skill.indexOf(";")));
                        skill=skill.substring(skill.indexOf(";")+1);
                    }
                    if (skill.indexOf(";")>0){
                        User.setSkill4(skill.substring(0,skill.indexOf(";")));
                        skill=skill.substring(skill.indexOf(";")+1);
                    }
                    if (skill.indexOf(";")>0){
                        User.setSkill5(skill.substring(0,skill.indexOf(";")));
                    }

                    flag = true;
                }
            }
            if(flag==true){
                sql = "update user set hyd=hyd+1 where user_id='"+id+"'";
                int result2 = stmt.executeUpdate(sql);
            }

            conn.close();
            return flag;

        }catch (SQLException e) {
            logger.error("MySQL操作错误"+ e);
            return false;
        }catch (Exception e){
            logger.error("登录时出现错误"+ e);
            return false;
        }
    }


}
