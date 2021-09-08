package test.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by lai on 2019/4/15.
 */
public class DatabaseConn {
    private static Logger logger = Logger.getLogger(test.dao.DatabaseConn.class);

    public static Connection getConn() {
        Connection conn = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://120.78.124.98:3306/cloud?useSSL=false&autoReconnect=true&failOverReadOnly=false&maxReconnects=10", "root", "123456");
        } catch (ClassNotFoundException e) {
            logger.error("没有找到需求类", e);//e.printStackTrace();
        } catch (SQLException e) {
            logger.error("Sql语句错误", e);//e.printStackTrace();
        }
        if(conn==null){
            logger.error("错误");
        }
        return conn;
    }

}
