package cn.ele.core.util.wechat;

public class WechatInfo {
    /**
     * 微信图文消息正文
     */
    private String content;
    /**
     * 封面图
     */
    private String cover;
    /**
     * 作者
     */
    private String author;
    /**
     * 标题
     */
    private String title;
    /**
     * 原文连接（点击阅读原文跳转的界面）
     */
    private String originallink;
    private String wechatabstract;

    public void setContent(String content) {
        this.content = content;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOriginallink(String originallink) {
        this.originallink = originallink;
    }

    public void setWechatabstract(String wechatabstract) {
        this.wechatabstract = wechatabstract;
    }

    public void setShowcover(String showcover) {
        this.showcover = showcover;
    }

    private String showcover;

    public String getWechatcontent() {
        return content;
    }

    public String getWechatcover() {
        return cover;
    }

    public String getWechatauthor() {
        return author;
    }

    public String getWechattitle() {
        return title;
    }

    public String getOriginallink() {
        return originallink;
    }

    public String getWechatabstract() {
        return wechatabstract;
    }

    public String getShowcover() {
        return showcover;
    }
}
