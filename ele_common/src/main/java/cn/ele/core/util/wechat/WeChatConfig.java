package cn.ele.core.util.wechat;

/**
 * @package :cn.ele.core.util.wechat
 *  * @Author        :fjnet
 * @Date :Create in 2018年 09月 28日  14:09 2018/9/28
 * @Description : ${description}
 * @Modified By :
 * @Version :&Version&
 **/
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class WeChatConfig {
    private static Properties props = new Properties();

    static {
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("WeChatConfig.properties"));
        } catch (FileNotFoundException var1) {
            var1.printStackTrace();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public WeChatConfig() {
    }

    public static String getValue(String key) {
        return props.getProperty(key);
    }

    public static void updateProperties(String key, String value) {
        props.setProperty(key, value);
    }
}
