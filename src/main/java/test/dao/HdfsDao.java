package test.dao;

import test.vo.Message;
import com.google.gson.Gson;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;

import java.net.URI;

/**
 * Created by lai on 2019/1/14.
 */
public class HdfsDao {
    private static Logger logger = Logger.getLogger(test.dao.HdfsDao.class);
    //将文件上传到HDFS中
    public static String copyFile(String local, String hdfsPath, Configuration conf){
        try {
            FileSystem fs = FileSystem.get(URI.create(hdfsPath),conf);
            fs.copyFromLocalFile(new Path(local),new Path(hdfsPath));
            fs.close();
        }catch (Exception e){
            logger.error("上传到HDFS过程中发生错误",e);
            return "hdfs_false";
        }
        return "success";
    }
}
