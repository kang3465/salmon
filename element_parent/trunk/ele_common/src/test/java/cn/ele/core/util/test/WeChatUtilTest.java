package cn.ele.core.util.test;

import cn.ele.core.util.WeChatUtil;
import cn.ele.core.util.wechat.WechatAccount;
import cn.ele.core.util.wechat.WechatInfo;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        JSONObject get = WeChatUtil.httpRequest(url, "get", null);
        System.out.println(get);
    }

    @Test
    public void readInputStream() {
    }

    @Test
    public void urlToFile() {

    }

    @Test
    public void uploadMedia() {
    }

    @Test
    public void batchgetMaterial() {
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