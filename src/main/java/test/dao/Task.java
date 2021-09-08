package test.dao;

import org.apache.log4j.Logger;
import test.vo.DataClass;
import test.vo.User;
import test.vo.UserClass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lai on 2019/5/18.
 */
public class Task {

    private static Logger logger = Logger.getLogger(test.dao.Task.class);

    //发布界面
    public static List<DataClass> putValue(int rwzt) throws IOException {

        try {
            List<DataClass> listDC=new ArrayList<>();
            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);
            String sql = "select * from rwb where rwzt=" + rwzt + " and user_id='"+User.getUser_id()+"'" ;
            logger.error("sql语句"+ sql);
            ResultSet rs = stmt.executeQuery(sql);
            DataClass dc;
            while(rs.next()) {
                dc=new DataClass();
                dc.setSj_id(rs.getString("rw_id"));
                dc.setRw_name(rs.getString("rw_name"));
                dc.setRwskill(rs.getString("rwskill"));
                dc.setPay(rs.getString("pay"));
                dc.setUrl(rs.getString("url"));
                dc.setDescri(rs.getString("descri"));
                if(rwzt>=2)
                    dc.setReceiver(rs.getString("receiver"));
                if(rwzt==5)
                    dc.setDafen(rs.getString("dafen"));
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

    //发布界面 已发布
    public static List<DataClass> putValue_0() throws IOException {

        List<DataClass> listDC;
        int rwzt=0;
        listDC=putValue(rwzt);
        return listDC;

    }

    //发布界面 待审核
    public static List<DataClass> putValue_1() throws IOException {

        List<DataClass> listDC;
        int rwzt=1;
       String sql = "select rw_id,rw_name,rwskill,pay,url,descri from rwb where rwzt=1 and user_id='"+User.getUser_id()+"'" ;
        listDC=putValue(rwzt);
        return listDC;

    }

    //发布界面 进行中
    public static List<DataClass> putValue_2() throws IOException {

        List<DataClass> listDC;
        int rwzt=2;
        listDC=putValue(rwzt);
        return listDC;

    }

    //发布界面 待查收
    public static List<DataClass> putValue_3() throws IOException {

        List<DataClass> listDC;
        int rwzt=3;
        listDC=putValue(rwzt);
        return listDC;

    }

    //发布界面 待评价
    public static List<DataClass> putValue_4() throws IOException {

        List<DataClass> listDC;
        int rwzt=4;
        listDC=putValue(rwzt);
        return listDC;

    }

    //发布界面 已完成
    public static List<DataClass> putValue_5() throws IOException {
        List<DataClass> listDC;
        int rwzt=5;
        listDC=putValue(rwzt);
        return listDC;

    }




    //领取界面
    public static List<DataClass> getValue(String sql) throws IOException {

        try {
            List<DataClass> listDC=new ArrayList<>();
            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);
            logger.error("sql语句"+ sql);
            ResultSet rs = stmt.executeQuery(sql);
            DataClass dc;
            while(rs.next()) {
                dc=new DataClass();
                dc.setSj_id(rs.getString("rw_id"));
                dc.setRw_name(rs.getString("rw_name"));
                dc.setRwskill(rs.getString("rwskill"));
                dc.setPay(rs.getString("pay"));
                dc.setUrl(rs.getString("url"));
                dc.setDescri(rs.getString("descri"));
                dc.setFaburen(rs.getString("user_id"));
                if(rs.getString("dafen")==""||rs.getString("dafen")==null)
                    dc.setDafen("暂未评分");
                else
                    dc.setDafen(rs.getString("dafen"));
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

    //领取界面 任务商店
    public static List<DataClass> getValue_0() throws IOException {
        List<DataClass> listDC;
        String sql = "select * from rwb where rwzt<=1 and (applicant is null or applicant not like '%" +User.getUser_id()+"%') ";
        listDC=getValue(sql);
        return listDC;

    }
    //领取界面 待审核
    public static List<DataClass> getValue_1() throws IOException {

        List<DataClass> listDC;
        String sql = "select * from rwb where rwzt=1 and applicant like '%"+ User.getUser_id()+"%'";
        listDC=getValue(sql);
        return listDC;
    }
    //领取界面 进行中
    public static List<DataClass> getValue_2() throws IOException {

        List<DataClass> listDC;
        String sql = "select * from rwb where rwzt=1 and finalworkerid='"+ User.getUser_id()+"'";
        listDC=getValue(sql);
        return listDC;

    }
    //领取界面 已完成
    public static List<DataClass> getValue_3() throws IOException {

//        String sql = "select rw_id,rw_name,rwskill,pay,url,descri,dafen from rwb where rwzt>=3 and user_id='"+ User.getUser_id()+"'";
        List<DataClass> listDC;
        String sql = "select * from rwb where rwzt>=3 and receiver='"+ User.getUser_id()+"'";
        listDC=getValue(sql);
        return listDC;
    }


    //自动界面 待领取
    public static List<DataClass> autoValue_1() throws IOException {

        List<DataClass> listDC;
        String sql = "select * from rwb s where s.rwzt=0 and s.rwskill in ('"+ User.getSkill1()+"','"+User.getSkill2()+"','"+User.getSkill3()+"','"
                +User.getSkill4()+"','"+User.getSkill5()+"') ORDER BY ABS(NOW() - s.rwtime) DESC limit 3";
        listDC=getValue(sql);
        return listDC;
    }
    //自动界面 进行中
    public static List<DataClass> autoValue_2() throws IOException {

        List<DataClass> listDC;
        String sql = "select * from rwb where rwzt=1 and finalworkerid='"+ User.getUser_id()+"'";
        listDC=getValue(sql);
        return listDC;

    }
    //自动界面 已完成
    public static List<DataClass> autoValue_3() throws IOException {

        List<DataClass> listDC;
        String sql = "select * from rwb where rwzt>=3 and rwlx=1 and receiver='"+ User.getUser_id()+"'";
        listDC=getValue(sql);
        return listDC;
    }


    public static List<UserClass> getUserInfo(String rw_id) throws IOException {

        try {
            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);
            String sql = "select applicant from rwb where rw_id="+ rw_id;

            logger.error("sql语句错误"+ sql);
            ResultSet rs = stmt.executeQuery(sql);

            String applicant="";
            while(rs.next()) {
                applicant=rs.getString("applicant");
            }
            logger.error("申请人："+applicant);
            List<String> appli=new ArrayList<>();


            while (applicant.length()>0){
                appli.add(applicant.substring(0,applicant.indexOf(";")));
                if(applicant.indexOf(";")==applicant.lastIndexOf(";"))
                    applicant="";
                else
                    applicant=applicant.substring(applicant.indexOf(";")+1);
            }

            List<UserClass> listuser=new ArrayList<>();
            for(int i=0;i<appli.size();i++){
                sql="select skill,rwwcl,rwdql,score from user where user_id='"+appli.get(i)+"'";
                rs = stmt.executeQuery(sql);
                UserClass usercl;
                while(rs.next()) {
                    usercl=new UserClass();
                    String skill=rs.getString("skill");
                    int rwwcl= Integer.valueOf(rs.getString("rwwcl"));
                    int rwdql= Integer.valueOf(rs.getString("rwdql"));
                    double score=Double.valueOf(rs.getString("score"));
                    double zhscore=3;
                    if(rwwcl!=0)
                        zhscore=rwwcl/(rwwcl+rwdql)*score;
                    usercl.setUser_id(appli.get(i));
                    usercl.setSkill(skill);
                    usercl.setRwwcl(rwwcl);
                    usercl.setRwdql(rwdql);
                    usercl.setScore(score);
                    usercl.setZhscore(zhscore);
                    listuser.add(usercl);
                }
            }
            conn.close();
            return listuser;
        }catch (SQLException e) {
            logger.error("MySQL操作错误"+ e);
            return null;
        }catch (Exception e){
            logger.error("注册时出现错误"+ e);
            return null;
        }

    }


    public static void Zhipai(String receiver_id) throws IOException {

        try {
            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);
            String sql = "update rwb set receiver='"+ receiver_id +"',rwzt=2 where rw_id="+ User.getSj_id()+"";
            int result2 = stmt.executeUpdate(sql);
            sql = "update user set rwdql=rwdql+1 where user_id="+ receiver_id+"";
            int result3 = stmt.executeUpdate(sql);
            User.setSj_id("");
            conn.close();

        }catch (SQLException e) {
            logger.error("MySQL操作错误"+ e);

        }catch (Exception e){
            logger.error("注册时出现错误"+ e);

        }
    }

    public static void Pingjia(String sj_id,String receiver,String dafen) throws IOException {

        try {
            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);
            String sql = "update rwb set rwzt=5,dafen="+dafen+" where rw_id="+ sj_id;
            int result2 = stmt.executeUpdate(sql);
            sql = "update user set score=rwpjl/(rwpjl+1)*score+"+dafen+"/(rwpjl+1),rwpjl=rwpjl+1 where user_id='"+ receiver+"'";
            int result3 = stmt.executeUpdate(sql);
            User.setSj_id("");
            conn.close();

        }catch (SQLException e) {
            logger.error("MySQL操作错误"+ e);

        }catch (Exception e){
            logger.error("注册时出现错误"+ e);

        }
    }

    public static void Shenqing(String sj_id) throws IOException {

        try {
            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);
            String sql = "select applicant from rwb where rw_id="+ sj_id;
            ResultSet rs = stmt.executeQuery(sql);
            String applicant="";
            while(rs.next()) {
                applicant=rs.getString("applicant");
            }
            if(applicant=="null"||applicant==null)
                applicant="";
            applicant+=User.getUser_id()+";";


            sql = "update rwb set rwzt=1,applicant='"+applicant+"' where rw_id="+ sj_id+"";
            int result2 = stmt.executeUpdate(sql);
            conn.close();

        }catch (SQLException e) {
            logger.error("MySQL操作错误"+ e);

        }catch (Exception e){
            logger.error("注册时出现错误"+ e);

        }
    }

    public static void Lingqu(String sj_id) throws IOException {

        try {
            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);
            String sql = "update rwb set receiver='"+ User.getUser_id() +"',rwzt=2 ,rwlx=1 where rw_id="+ sj_id;
            int result2 = stmt.executeUpdate(sql);
            sql = "update user set rwdql=rwdql+1 where user_id="+ User.getUser_id()+"";
            int result3 = stmt.executeUpdate(sql);
            conn.close();
        }catch (SQLException e) {
            logger.error("MySQL操作错误"+ e);

        }catch (Exception e){
            logger.error("注册时出现错误"+ e);

        }
    }

}
