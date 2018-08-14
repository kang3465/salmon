package cn.ele.core.util.wechat;

public class WechatInfo {
    private String content;
    private String cover;
    private String author;
    private String title;
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
