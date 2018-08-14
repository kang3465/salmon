package cn.ele.core.util.wechat;

import cn.ele.core.util.WeChatUtil;

public class WechatAccount {
    public String getAppid() {
        return WeChatUtil.appid;
    }

    public String getAppkey() {
        return WeChatUtil.secret;
    }
}
