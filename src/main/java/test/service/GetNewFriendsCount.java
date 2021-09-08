package test.service;

import org.apache.log4j.Logger;
import test.dao.DatabaseConn;
import test.vo.FriendClass;
import test.vo.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by lai on 2019/5/10.
 */
public class GetNewFriendsCount {

    private static Logger logger = Logger.getLogger(test.service.GetNewFriendsCount.class);
    public static String getValue() throws IOException, SQLException {

        String FriendCount="0";

        Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
        Statement stmt = conn.createStatement();
        String sqlusedb = "use cloud";
        int result1 = stmt.executeUpdate(sqlusedb);

        String sql = "select count(id) from gxb where user_2='"+User.getUser_id()+"' and gx=0";
        logger.error("sql语句" + sql);
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) {
            FriendCount=rs.getString("count(id)");
        }
        conn.close();
        return FriendCount;
    }


}
