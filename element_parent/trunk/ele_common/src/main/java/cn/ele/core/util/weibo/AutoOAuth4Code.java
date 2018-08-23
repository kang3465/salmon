package cn.ele.core.util.weibo;

import cn.ele.core.util.weibo.http.AccessToken;
import cn.ele.core.util.weibo.util.WeiboConfig;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Title: TRS 内容协作平台（TRS WCM） <BR>
 * Description: 自动延续新浪账号授权<BR>
 * <BR>
 * Copyright: Copyright (c) 2004-2013 北京拓尔思信息技术股份有限公司 <BR>
 * Company: www.trs.com.cn <BR>
 * 
 * @author lky
 * @version 1.0
 */
public class AutoOAuth4Code {

    // 日志记录类
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
            .getLogger(AutoOAuth4Code.class);

    /**
     * 从跳转地址中获取code的值
     * 
     * @param location
     *            跳转url
     * @return code的值
     */
    private static String getCodeFromLocation(String location) {
        int begin = location.indexOf("code=");
        return location.substring(begin + 5);
    }

    /**
     * 
     * @param username
     *            用户名
     * @param password
     *            密码
     * @return token对象
     */
    public static AccessToken AccessTokenRefresh(String username, String password) {
        AccessToken access_token = null;
        String location = "";
        String code = "";
        Oauth oauth = new Oauth();
        HttpClient client = new HttpClient();
        WeiboLogin wl = new WeiboLogin();
        wl.setUsername(username);
        wl.setPassword(password);
        String url = null;
        try {
            // 获得授权url
            url = oauth.authorize("code","");

            // 模拟登录的代码（判断微博账号自动登录是否登录成功）
            boolean isLogined = wl.login();
            if (!isLogined) {
                logger.error("用户名、密码错误！");
                return null;
            }

            // 获取登录cookie
            String cookie = wl.getCookie();
           
            // 自动进入授权页面
            HttpMethod method = new PostMethod(url);
            method.addRequestHeader("Cookie", cookie);
            int statusCode = client.executeMethod(method);
           
            // 已授权过的，刷新增加token过期时间
            if (302 == statusCode) {

                // 获取跳转url
                logger.debug("已授权过的账号，刷新增加token过期时间");
                location = method.getResponseHeader("Location").getValue();

                // 从跳转url中抓出code
                code = getCodeFromLocation(location);

                // 使用code换取access token
                access_token = oauth.getAccessTokenByCode(code);
            }
            else if(200 == statusCode){        // 未授权，需要模拟授权操作
                logger.debug("未授权过的账号，需要模拟授权操作");

                // 自动组装提交请求页面
                Document html = Jsoup.parse(method.getResponseBodyAsString());
                Elements params = html.select("form[name=authZForm] > input[type=hidden]");
                PostMethod post = new PostMethod("https://api.weibo.com/oauth2/authorize");
               
                // 设置请求报头
                post.addRequestHeader("Cookie", cookie);
                post.addRequestHeader("Referer", "https://api.weibo.com/oauth2/authorize?client_id=" +
                                            WeiboConfig.getValue("client_ID").trim() +
                                            "&redirect_uri=" +
                                            WeiboConfig.getValue("redirect_URI").trim() +
                                            "&response_type=code");
                // 填充post请求的各个参数
                for(Element param : params) {
                    post.addParameter(param.attr("name"), param.attr("value"));
                }

                // 提交请求获取请求状态
                statusCode = client.executeMethod(post);
               
                // 302 跳转则表示post成功
                if(302 == statusCode) {
                    // 获取跳转url
                    location = post.getResponseHeader("Location").getValue();
                   
                    // 获取code换取access_token
                    code = getCodeFromLocation(location);
                    access_token = oauth.getAccessTokenByCode(code);
                } else {
                    logger.debug("未获取到跳转状态，授权失败！");
                    return null;
                }
            } else {
                logger.debug("未获取到正确跳转状态，授权失败！");
                return null;
            }
        } catch (Exception e) {
            logger.error("未获取到正确跳转状态，授权失败！");
        }
        return access_token;
    }

    /**
     * 测试获取AccessToken是否正确
     * 
     * @param args
     */
    public static void main(String[] args) {

        // 需要正确的新浪账号的用户名和密码
        String username = "";
        String password = "";
        try {
            AccessToken oAccessToken = AutoOAuth4Code.AccessTokenRefresh(
                    username, password);
            System.out.println("获取到的："
                    +oAccessToken.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        
    }
}