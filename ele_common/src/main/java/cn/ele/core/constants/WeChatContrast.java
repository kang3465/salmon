package cn.ele.core.constants;

import cn.ele.core.util.wechat.WeChatConfig;

/**
 * @package :cn.ele.core.service.wechat
 *  * @Author        :fjnet
 * @Date :Create in 2018年 09月 30日  10:47 2018/9/30
 * @Description : ${description}
 * @Modified By :
 * @Version :&Version&
 **/
public interface WeChatContrast {
    /**
     * #微信开放平台接口基础连接
     */
    String BASE_URL= WeChatConfig.getValue("BASE_URL");
    /**
     * # 获取access_token  https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
     */
    String TOKEN =  WeChatConfig.getValue("TOKEN");
    /**
     * #上传图文消息内的图片获取URL
     */
    String UPLOAD_IMG =  WeChatConfig.getValue("UPLOAD_IMG");
    /**
     * #添加永久素材（封面图，图片，视频，音频） 获得素材ID
     */
    String UPLOAD_MEDIA =  WeChatConfig.getValue("UPLOAD_MEDIA");
    /**
     * #上传图文消息素材
     */
    String UPLOAD_NEWS =  WeChatConfig.getValue("UPLOAD_NEWS");
    /**
     * #新增永久图文素材
     */
    String ADD_NEWS =  WeChatConfig.getValue("ADD_NEWS");
    /**
     * #根据标签进行群发(图文消息，文本，语音/音频，图片)
     */
    String MASS_SEND_ALL =  WeChatConfig.getValue("MASS_SEND_ALL");
    /**
     * #删除群发
     */
    String MASS_DELETE =  WeChatConfig.getValue("MASS_DELETE");
    /**
     * #删除永久素材（临时素材无法通过本接口删除，暂时没找到删除临时素材的方法）
     */
    String DEL_MATERIAL =  WeChatConfig.getValue("DEL_MATERIAL");
    /**
     * #打开已群发文章评论（新增接口）
     */
    String COMMENT_OPEN =  WeChatConfig.getValue("COMMENT_OPEN");
    /**
     * #关闭已群发文章评论（新增接口）
     */
    String COMMENT_CLOSE =  WeChatConfig.getValue("COMMENT_CLOSE");
    /**
     * #查看指定文章的评论数据（新增接口）
     */
    String COMMENT_LIST =  WeChatConfig.getValue("COMMENT_LIST");
    /**
     * #将评论标记精选（新增接口）
     */
    String COMMENT_MARKELECT =  WeChatConfig.getValue("COMMENT_MARKELECT");
    /**
     * #将评论取消精选
     */
    String COMMENT_UNMARKELECT =  WeChatConfig.getValue("COMMENT_UNMARKELECT");
    /**
     * #删除评论（新增接口）
     */
    String COMMENT_DELETE =  WeChatConfig.getValue("COMMENT_DELETE");
    /**
     * # 回复评论（新增接口）
     */
    String COMMENT_REPLY_ADD =  WeChatConfig.getValue("COMMENT_REPLY_ADD");
    /**
     * # 删除回复（新增接口）
     */
    String COMMENT_REPLY_DELETE =  WeChatConfig.getValue("COMMENT_REPLY_DELETE");
    /**
     * #获取用户基本信息（包括UnionID机制）
     */
    String USER_INFO =  WeChatConfig.getValue("USER_INFO");
    /**
     * #批量获取用户基本信息
     */
    String USER_INFO_BATCHGET =  WeChatConfig.getValue("USER_INFO_BATCHGET");
    /**
     * #获取用户列表
     */
    String USER_GET =  WeChatConfig.getValue("USER_GET");
    /**
     *
     */
    String EXAMPLE =  WeChatConfig.getValue("EXAMPLE");
    /**
     *
     */
    String EXAMPLE1 =  WeChatConfig.getValue("EXAMPLE1");

}
