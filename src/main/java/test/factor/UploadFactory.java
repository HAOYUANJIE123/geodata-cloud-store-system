package test.factor;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.util.Properties;

/**
 * Created by lai on 2019/1/14.
 */
public class UploadFactory {
    public static ServletFileUpload getUpload(Properties properties, String tmp){
        int maxFileSize = 1000 * 1024 * 1024;
        int maxMemSize = 1000 * 1024 * 1024;
        //String contentType = request.getContentType();
//        int a = contentType.indexOf("multipart/from-data");
//        if ((contentType.indexOf("multipart/from-data")) >= 0){
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置内存中存储文件的最大值
        factory.setSizeThreshold(maxMemSize);
        //设置工厂的临时文件目录：当上传文件的大小大于缓冲区大小时，将使用临时文件目录缓存上传的文件
        factory.setRepository(new File(properties.getProperty(tmp)));
        //创建一个新的文件上传处理程序
        ServletFileUpload upload = new ServletFileUpload(factory);
        //设置最大上传的文件大小
        upload.setSizeMax(maxFileSize);
        upload.setHeaderEncoding("UTF-8");
        return upload;
    }
}
