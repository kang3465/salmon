package cn.ele.core.util.test;

import cn.ele.core.util.HttpClient;
import cn.ele.core.util.WeChatUtil;
import cn.ele.core.util.wechat.WechatAccount;
import cn.ele.core.util.wechat.WechatInfo;
import cn.ele.core.util.weibo.AutoOAuth4Code;
import net.sf.json.JSONObject;
import org.junit.Test;
import weibo4j.http.AccessToken;
import weibo4j.util.WeiboConfig;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class WeChatUtilTest {

        private String access_token = "13_z7R689bHIjTZUh5ipVuJu7fDDUJLDYvr2xpsDDRPxy_VFnEgHuyTmBvYJUO7znLC8QpTY7mc_cneFSuyVJe2iykCBiRKY_EKAxFZFjvXRXHQpncqPhrja3x5RGjs5ZCi2reuNK9yJuI_KUAcRBNgAEANXJ";

    @Test
    public void getAccessToken() {;
        WechatAccount wechatAccount = new WechatAccount();
        wechatAccount.setAppid("wx384ee1648c99282d");
        wechatAccount.setSecret("1f2f87d67c1ec62dac936e0ae07cbaa1");
        String accessToken = WeChatUtil.getAccessToken(wechatAccount.getAppid(), wechatAccount.getSecret());
        System.out.println(accessToken);
    }

    @Test
    public void tes111() {
        String pids = "11,";
        pids = pids.substring(0,pids.length()-1);
        System.out.println(pids);
    }
    @Test
    public void httpRequest() {
        List<WechatInfo> wechatInfoList = new ArrayList<>();
        WechatInfo wechatInfo = new WechatInfo();
        wechatInfo.setAuthor("康小笺");
        wechatInfo.setCover("D:\\尚善吉祥\\593277516412350740.png");
        wechatInfo.setContent("测试详情");
        wechatInfo.setOriginallink("123");
        wechatInfo.setShowcover("1");
        wechatInfo.setTitle("123");
        wechatInfo.setWechatabstract("123");
        wechatInfoList.add(wechatInfo);
        String send = WeChatUtil.send(wechatInfoList, new WechatAccount());
        System.out.println(send);
    }

    @Test
    public void getUserOpenIDs() {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token="+access_token;
        JSONObject get = WeChatUtil.httpsRequest(url, "get", null);
        System.out.println(get);
    }

    @Test
    public void readInputStream() throws IOException, ParseException {//218.246.5.130  101.200.56.109:8088
        HttpClient httpClient = new HttpClient("http://218.246.5.130/wbtt");
        HashMap<String, String > contextmap = new HashMap<>();
        contextmap.put("access_token", "2.00gM6KUD0H7OeJ07d2831e6f0EC6v1");
        contextmap.put("source", WeiboConfig.getValue("client_ID"));
        contextmap.put("rip", "218.246.5.130");
        contextmap.put("title", "标题");
        contextmap.put("content", "正文内容");
        contextmap.put("cover","http://tva2.sinaimg.cn/crop.0.0.200.200.50/be6325a6jw1f06ykb8i44j205k05kglf.jpg");
        contextmap.put("summary", "daodao=导语");
        contextmap.put("text", "短微博内容");//text	string	是	与其绑定短微博内容，限制1900个中英文字符内VERIFIERverifier


        httpClient.setParameter(contextmap);
        httpClient.post();

        String content = httpClient.getContent();
        System.out.println(content);
    }

    @Test
    public void urlToFile() throws IOException {
        Properties props = new Properties();
        props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("WeChatConfig.properties"));
        String property = props.getProperty("nameList");
        property=new String(property.getBytes("ISO-8859-1"),"gbk");
        System.out.println(property);

        String gbk = props.getProperty(new String(property.getBytes("gbk"),"ISO-8859-1"));
        System.out.println(gbk);

    }

    @Test
    public void uploadMedia() throws IOException, ParseException {
        String URL = "https://api.weibo.com/oauth2/authorize";
        String client_id = WeiboConfig.getValue("client_ID");
        String redirect_uri = WeiboConfig.getValue("redirect_URI");
        String scope = "all";
        Map<String ,String> parm = new HashMap<>();
        parm.put("client_id",client_id);
        parm.put("redirect_uri",redirect_uri);
        parm.put("scope",scope);
        HttpClient httpClient = new HttpClient(URL, parm);
        httpClient.post();
        String content1 = httpClient.getContent();

    }

    @Test
    public void batchgetMaterial() throws IOException, ParseException {
        AccessToken accessToken = AutoOAuth4Code.AccessTokenRefresh("16619884445", "wb.123456");
        Map<String ,String> parm = new HashMap<>();
        String accessToken1 = accessToken.getAccessToken();
        String title = "ceshi";
        String content= "neitong";
        String cover = "http://218.246.5.130//webpic/W0201809/W020180906/W020180906512588056443.jpg";
        String summary = "daoyu";
        String text = "短微博内容";
        parm.put("title",title);
        parm.put("content",content);
        parm.put("cover",cover);
        parm.put("summary",summary);
        parm.put("text",text);
        parm.put("accessToken",accessToken1);
        HttpClient httpClient = new HttpClient("https://api.weibo.com/proxy/article/publish.json", parm);
        httpClient.post();
        String content1 = httpClient.getContent();
    }

    @Test
    public void uploadImg() {

    }

   /* @Test
    public void send() {
        List<WechatInfo> wechatInfoList = new ArrayList<>();
        WechatInfo wechatInfo = new WechatInfo();
        wechatInfo.setAuthor("kang");
        wechatInfo.setCover("D:\\尚善吉祥\\593277516412350740.png");
        wechatInfo.setContent("测试详情");
        wechatInfo.setOriginallink("123");
        wechatInfo.setShowcover("1");
        wechatInfo.setTitle("123");
        wechatInfo.setWechatabstract("123");
        wechatInfoList.add(wechatInfo);
        String send = WeChatUtil.send(wechatInfoList, new WechatAccount());
        System.out.println(send);
    }*/
}