package test.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by lai on 2019/4/16.
 */
public class GetShiName {
    //获取Hadoop相关路径下文件的文件列表
    public static List<String> getFileList() throws IOException, SQLException {

        List<String> shiName = new ArrayList<String>();
        Connection conn = DatabaseConn.getConn();// 建立数据库连接对象
        Statement stmt = conn.createStatement();
        String  sqlusedb="use cloud";
        int result1 = stmt.executeUpdate(sqlusedb);

        String sql = "select distinct shi from sjb";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            shiName.add(rs.getString("shi"));
        }
        return shiName;
    }


}
