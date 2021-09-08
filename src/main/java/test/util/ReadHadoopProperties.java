package test.util;

import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * Created by lai on 2019/1/14.
 */
public class ReadHadoopProperties {
    Logger logger = Logger.getLogger(getClass());
    public Properties getHadoopProperties() {
        Properties hadoopProps = new Properties();
        try {
            hadoopProps.load(getClass().getResourceAsStream("/hadoop.properties"));//读取hadoop.properties配置文件
        }catch (Exception e){
            logger.error("读取配置文件错误",e);
        }
        return hadoopProps;
    }
}
