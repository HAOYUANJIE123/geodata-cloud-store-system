package test.dao;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;

import java.net.URI;

/**
 * Created by lai on 2019/1/17.
 */
public class HdfsToLocal {
    private static Logger logger = Logger.getLogger(test.dao.HdfsToLocal.class);

    //将文件由HDFS下载到云服务器本地
    public static String copyFile(String path, String local, String hdfsPath, Configuration conf) {
        try {
            FileSystem fs = FileSystem.get(URI.create(path), conf,"hadoop");
            fs.copyToLocalFile(new Path(hdfsPath) , new Path(local));
            fs.close();
        } catch (Exception e) {
            logger.error("文件由HDFS下载到本地服务器过程中发生错误", e);
            return "hdfs_false";
        }
        return "success";
    }
}
