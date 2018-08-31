package cn.ele.core.util.wechat;

import cn.ele.core.util.WeChatUtil;

public class WechatAccount {

    private String appid;

    private String secret;

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getAppid() {
        return appid;
    }

    public String getAppkey() {
        return WeChatUtil.secret;
    }
}
