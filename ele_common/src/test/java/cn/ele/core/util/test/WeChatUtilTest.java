package cn.ele.core.util.test;

import cn.ele.core.util.WeChatUtil;
import cn.ele.core.util.wechat.WechatAccount;
import cn.ele.core.util.wechat.WechatInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class WeChatUtilTest {

    @Test
    public void getAccessToken() {;
        WechatAccount wechatAccount = new WechatAccount();

        String accessToken = WeChatUtil.getAccessToken(wechatAccount.getAppid(), wechatAccount.getAppkey());
        System.out.println(accessToken);
    }

    @Test
    public void httpRequest() {
    }

    @Test
    public void getUserOpenIDs() {
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

    @Test
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
    }
}