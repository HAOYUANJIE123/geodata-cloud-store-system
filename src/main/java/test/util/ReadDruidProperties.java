package test.util;

import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * Created by lai on 2019/1/14.
 */
public class ReadDruidProperties {
    Logger logger = Logger.getLogger(getClass());
    public Properties getDruidProperties() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/file.properties"));//读取file.properties配置文件
        }catch (Exception e){
            logger.error("读取配置文件错误",e);
        }
        return properties;
    }
}
