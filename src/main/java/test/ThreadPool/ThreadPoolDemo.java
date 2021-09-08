package test.ThreadPool;

import org.apache.commons.lang.RandomStringUtils;
import test.factor.GsonFactory;
import test.dao.HdfsDao;
import org.apache.commons.fileupload.FileItem;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import test.service.Upload;
import test.service.setSize;
import test.vo.FileClass;
import test.vo.FilePack;
import test.vo.Message;

import java.io.File;
import java.net.URI;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by lai on 2019/1/14.
 */
public class ThreadPoolDemo {
    private static Logger logger = Logger.getLogger(test.ThreadPool.ThreadPoolDemo.class);

    public String threadPoolDemo1(Iterator i, Properties hadoopProps, String filePath, FilePack filePack) {//通过线程将上传文件先上传至云服务器本地，继而传到hadfs
        //在线程池中创建三个线程
        ExecutorService thredPool = Executors.newFixedThreadPool(3);
        //从线程池获取一个线程，并执行该线程代码
        //final Iterator i1 = i;
        final Properties hadoopProps1 = hadoopProps;
        final String filePath1 = filePath;
        String threadresult = "";

/**********    UD1   ************/
        //i.next();
        while (i.hasNext()) {
            final FileItem fileItem = (FileItem) i.next();
            String fileName = fileItem.getName();
            String fileSize="";
            final String dataName= RandomStringUtils.randomAlphanumeric(15)+fileName.substring(fileName.lastIndexOf("."));
            logger.error("获取上传文件名和大小");
            if(fileName==null){
                break;
            }
            fileSize= setSize.setSize(fileItem.getSize());
            filePack.fileclass=new FileClass(fileName,fileSize,dataName);
            Future<String> f1 = thredPool.submit(new Callable<String>() {
                public String call() {
                    String uploadresult="";
                    if(fileItem!=null)
                        uploadresult= Upload.Upload(fileItem, hadoopProps1, filePath1,dataName );
                    return uploadresult;
                }
            });

            try {
                threadresult += f1.get();
            } catch (Exception e) {
                logger.error("处理上传文件时出现错误",e);
            }
        }

        //关闭线程池
        thredPool.shutdown();
        return GsonFactory.getGson().toJson(new Message(threadresult));
    }
}
