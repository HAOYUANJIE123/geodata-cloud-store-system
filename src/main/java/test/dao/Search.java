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

import static java.lang.Math.abs;
import static java.lang.StrictMath.random;

/**
 * Created by lai on 2019/4/23.
 */
public class Search {

    private static Logger logger = Logger.getLogger(test.dao.Search.class);

    public static List<DataClass> getValue(String name) throws IOException {

        String str ="";
        try {
            List<DataClass> listDC=new ArrayList<>();
            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);
            String sql = "select sj_id,sj_name,locality,size,type,cxtime,wn_coordinate,es_coordinate,user_id from sjb where sj_name='"+name+"' or locality like '%"+name+"%'";
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
                dc.setScuser(rs.getString("user_id"));
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


    public static List<DataClass> getAdvValue(String str) throws IOException {
        try {
            List<DataClass> listDC=new ArrayList<>();
            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);
            String sql = "select sj_id,sj_name,locality,size,type,cxtime,wn_coordinate,es_coordinate,user_id from sjb where " + str;
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
                dc.setScuser(rs.getString("user_id"));
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

    public static List<DataClass> getRangeValue(String wn_coord,String es_coord) throws IOException {
        try {
            double wn_long = Double.parseDouble(wn_coord.substring(0,wn_coord.lastIndexOf(",")));
            double wn_lat = Double.parseDouble(wn_coord.substring(wn_coord.lastIndexOf(",")+1));
            double es_long = Double.parseDouble(es_coord.substring(0,es_coord.lastIndexOf(",")));
            double es_lat = Double.parseDouble(es_coord.substring(es_coord.lastIndexOf(",")+1));
            List<DataClass> listDC=new ArrayList<>();
            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);
            String sql = "select sj_id,sj_name,locality,size,type,cxtime,wn_coordinate,es_coordinate,user_id from sjb";
            logger.error("sql语句错误"+ sql);
            ResultSet rs = stmt.executeQuery(sql);
            DataClass dc;
            while(rs.next()) {
                double wn_1 = Double.parseDouble(rs.getString("wn_coordinate").substring(0,rs.getString("wn_coordinate").lastIndexOf(",")));
                double wn_2 = Double.parseDouble(rs.getString("wn_coordinate").substring(rs.getString("wn_coordinate").lastIndexOf(",")+1));
                double es_1 = Double.parseDouble(rs.getString("es_coordinate").substring(0,rs.getString("es_coordinate").lastIndexOf(",")));
                double es_2 = Double.parseDouble(rs.getString("es_coordinate").substring(rs.getString("es_coordinate").lastIndexOf(",")+1));
                double zx = abs(wn_long + es_long - wn_1 - es_1);
                double x  = abs(wn_long - es_long) + abs(wn_1 - es_1);
                double zy = abs(wn_lat + es_lat - wn_2 - es_2);
                double y  = abs(wn_lat - es_lat) + abs(wn_2 - es_2);
                if(zx <= x && zy <= y)
                {
                    dc=new DataClass();
                    dc.setSj_id(rs.getString("sj_id"));
                    dc.setSj_name(rs.getString("sj_name"));
                    dc.setLocality(rs.getString("locality"));
                    dc.setSize(rs.getString("size"));
                    dc.setType(rs.getString("type"));
                    dc.setCxtime(rs.getString("cxtime"));
                    dc.setWn_coord(rs.getString("wn_coordinate"));
                    dc.setEs_coord(rs.getString("es_coordinate"));
                    dc.setScuser(rs.getString("user_id"));
                    listDC.add(dc);
                }

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

    public static List<DataClass> getUserValue() throws IOException {
        try {

            List<DataClass> listDC=new ArrayList<>();

            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);
            String sql = "select sj_id,sj_name,locality,size,type,cxtime,wn_coordinate,es_coordinate,user_id from sjb where receiver='"+ User.getUser_id()+"' and sfrecepted=0";
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
                dc.setScuser(rs.getString("user_id"));
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


    public static List<DataClass> get_taskinfo() throws IOException {
        //  String name="";
       /* try {

            List<DataClass> listDC=new ArrayList<>();

            Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);
            String sql = "select sj_id,sj_name,locality,size,type,cxtime,wn_coordinate,es_coordinate from sjb where sj_name='"+name+"' or locality like '%"+name+"%'";
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
            return listDC;


        }catch (SQLException e) {
            logger.error("MySQL操作错误"+ e);
            return null;
        }*/
        List<DataClass> listDC=new ArrayList<>();

//        for(int i=0;i<5;i++)
//        {
//            DataClass dc=new DataClass();
//            dc.setSj_id(String.valueOf(random()));
//            dc.setSj_name(String.valueOf(random()));
//            dc.setLocality(String.valueOf(random()));
//            dc.setSize(String.valueOf(random()));
//            dc.setType(String.valueOf(random()));
//            dc.setCxtime(String.valueOf(random()));
//            dc.setWn_coord(String.valueOf(random()));
//            dc.setEs_coord(String.valueOf(random()));
//            listDC.add(dc);
//        }
        DataClass dc=new DataClass();
        dc.setSj_id("桥");
        dc.setSj_name("什么");
        dc.setLocality(String.valueOf(random()));
        dc.setSize(String.valueOf(random()));
        dc.setType(String.valueOf(random()));
        dc.setCxtime("哦");
        dc.setWn_coord(String.valueOf(random()));
        dc.setEs_coord(String.valueOf(random()));
        listDC.add(dc);
        return listDC;

    }

}
