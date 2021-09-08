package test.factor;

import com.google.gson.Gson;
import test.util.ReadDruidProperties;
import test.util.ReadHadoopProperties;

import java.util.Properties;

/**
 * Created by lai on 2019/1/14.
 */
public class ReadPropertiesFactory {
    public static Properties getDruidProperties(){
        Properties properties = new ReadDruidProperties().getDruidProperties();//将配置文件file.properties赋给properties
        return properties;
    }
    public static Properties getHadoopProperties(){
        Properties hadoopProps = new ReadHadoopProperties().getHadoopProperties();//将配置文件Hadoop.properties赋给hadoopProps
        return hadoopProps;
    }
}
