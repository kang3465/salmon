//package cn.ele.core.util.test;
//
//import cn.ele.core.util.HttpClient;
//import cn.ele.core.util.WeChatUtil;
//import cn.ele.core.util.wechat.WechatAccount;
//import cn.ele.core.util.wechat.WechatInfo;
//import cn.ele.core.util.weibo.AutoOAuth4Code;
//import cn.ele.core.util.weibo.SSLClient;
//import cn.ele.core.util.weibo.WeiboLogin;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.HttpEntityWrapper;
//import org.apache.http.message.BasicNameValuePair;
//import org.junit.Test;
//import weibo4j.http.AccessToken;
//import weibo4j.util.WeiboConfig;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.text.ParseException;
//import java.util.*;
//import java.util.zip.GZIPInputStream;
//
//public class WeChatUtilTest {
//
//        private String access_token = "13_z7R689bHIjTZUh5ipVuJu7fDDUJLDYvr2xpsDDRPxy_VFnEgHuyTmBvYJUO7znLC8QpTY7mc_cneFSuyVJe2iykCBiRKY_EKAxFZFjvXRXHQpncqPhrja3x5RGjs5ZCi2reuNK9yJuI_KUAcRBNgAEANXJ";
//
//    @Test
//    public void getAccessToken() {;
//        WechatAccount wechatAccount = new WechatAccount();
//        wechatAccount.setAppid("wx384ee1648c99282d");
//        wechatAccount.setSecret("1f2f87d67c1ec62dac936e0ae07cbaa1");
//        String accessToken = WeChatUtil.getAccessToken(wechatAccount.getAppid(), wechatAccount.getSecret());
//        System.out.println(accessToken);
//    }
//
//    @Test
//    public void tes111() {
//        String pids = "11,";
//        pids = pids.substring(0,pids.length()-1);
//        System.out.println(pids);
//    }
//    @Test
//    public void httpRequest() {
//        List<WechatInfo> wechatInfoList = new ArrayList<>();
//        WechatInfo wechatInfo = new WechatInfo();
//        wechatInfo.setAuthor("康小笺");
//        wechatInfo.setCover("D:\\尚善吉祥\\593277516412350740.png");
//        wechatInfo.setContent("测试详情");
//        wechatInfo.setOriginallink("123");
//        wechatInfo.setShowcover("1");
//        wechatInfo.setTitle("123");
//        wechatInfo.setWechatabstract("123");
//        wechatInfoList.add(wechatInfo);
//        String send = WeChatUtil.send(wechatInfoList, new WechatAccount());
//        System.out.println(send);
//    }
//
//    @Test
//    public void getUserOpenIDs() {
//        String url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token="+access_token;
//        JSONObject get = WeChatUtil.httpsRequest(url, "get", null);
//        System.out.println(get);
//    }
//
//    @Test
//    public void readInputStream() throws IOException, ParseException {//218.246.5.130  101.200.56.109:8088
//        HttpClient httpClient = new HttpClient("http://218.246.5.130/wbtt");
//        HashMap<String, String > contextmap = new HashMap<>();
//        contextmap.put("access_token", "2.00gM6KUD0H7OeJ07d2831e6f0EC6v1");
//        contextmap.put("source", WeiboConfig.getValue("client_ID"));
//        contextmap.put("rip", "218.246.5.130");
//        contextmap.put("title", "标题");
//        contextmap.put("content", "正文内容");
//        contextmap.put("cover","http://tva2.sinaimg.cn/crop.0.0.200.200.50/be6325a6jw1f06ykb8i44j205k05kglf.jpg");
//        contextmap.put("summary", "daodao=导语");
//        contextmap.put("text", "短微博内容");//text	string	是	与其绑定短微博内容，限制1900个中英文字符内VERIFIERverifier
//
//
//        httpClient.setParameter(contextmap);
//        httpClient.post();
//
//        String content = httpClient.getContent();
//        System.out.println(content);
//    }
//
//    @Test
//    public void urlToFile() throws IOException {
//        String baseUrl = "http://218.246.5.130/wechat/";
//        String url = "";
//        String accessToken = WeChatUtil.getAccessToken("wx384ee1648c99282d", "fc956c634d357b7d4908ddd49a416d04");
//        System.out.println("accessToken:"+accessToken);
//        url = "cgi-bin/user/get?access_token="+accessToken+"&next_openid=";
//
//        JSONObject jsonObject = WeChatUtil.httpAndHttpsRequest(baseUrl + url, "GET", "");
//
//
//        String string = jsonObject.toString();
//        System.out.println(string);
//
//        String next_openid = (String) jsonObject.get("next_openid");
//        JSONObject data = (JSONObject) jsonObject.get("data");
//        JSONArray openid = (JSONArray) data.get("openid");
//        for (int i = 0; i <openid.size() ; i++) {
//            String o = (String) openid.get(i);
//            JSONObject json = WeChatUtil.httpAndHttpsRequest(baseUrl + "cgi-bin/user/info?access_token="+accessToken+"&openid="+o+"&lang=zh_CN", "GET", "");
//            System.out.println(json.toString());
//            String oid = (String) json.get("openid");
//            String nickname = (String) json.get("nickname");
//            if ("康小笺".equals(nickname))
//            System.out.println(oid+":-----："+nickname);
//        }
//        jsonObject = WeChatUtil.httpAndHttpsRequest(baseUrl + url+next_openid, "GET", "");
//        string = jsonObject.toString();
//        System.out.println(string);
//
//        next_openid = (String) jsonObject.get("next_openid");
//        data = (JSONObject) jsonObject.get("data");
//        openid = (JSONArray) data.get("openid");
//        for (int i = 0; i <openid.size() ; i++) {
//            String o = (String) openid.get(i);
//            JSONObject json = WeChatUtil.httpAndHttpsRequest(baseUrl + "cgi-bin/user/info?access_token="+accessToken+"&openid="+o+"&lang=zh_CN", "GET", "");
//            String oid = (String) json.get("openid");
//            String nickname = (String) json.get("nickname");
//            if ("康小笺".equals(nickname))
//                System.out.println(oid+":-----："+nickname);
//        }
//        jsonObject = WeChatUtil.httpAndHttpsRequest(baseUrl + url+next_openid, "GET", "");
//        string = jsonObject.toString();
//        System.out.println(string);
//
//        next_openid = (String) jsonObject.get("next_openid");
//        data = (JSONObject) jsonObject.get("data");
//        openid = (JSONArray) data.get("openid");
//        for (int i = 0; i <openid.size() ; i++) {
//            String o = (String) openid.get(i);
//            JSONObject json = WeChatUtil.httpAndHttpsRequest(baseUrl + "cgi-bin/user/info?access_token="+accessToken+"&openid="+o+"&lang=zh_CN", "GET", "");
//            String oid = (String) json.get("openid");
//            String nickname = (String) json.get("nickname");
//            if ("康小笺".equals(nickname))
//                System.out.println(oid+":-----："+nickname);
//        }jsonObject = WeChatUtil.httpAndHttpsRequest(baseUrl + url+next_openid, "GET", "");
//        string = jsonObject.toString();
//        System.out.println(string);
//
//        next_openid = (String) jsonObject.get("next_openid");
//        data = (JSONObject) jsonObject.get("data");
//        openid = (JSONArray) data.get("openid");
//        for (int i = 0; i <openid.size() ; i++) {
//            String o = (String) openid.get(i);
//            JSONObject json = WeChatUtil.httpAndHttpsRequest(baseUrl + "cgi-bin/user/info?access_token="+accessToken+"&openid="+o+"&lang=zh_CN", "GET", "");
//            String oid = (String) json.get("openid");
//            String nickname = (String) json.get("nickname");
//            if ("康小笺".equals(nickname))
//                System.out.println(oid+":-----："+nickname);
//        }
//    }
//
//    @Test
//    public void ttttt() throws IOException {
//        String baseUrl = "http://218.246.5.130/wechat/";
//        String url = "";
//        String accessToken = WeChatUtil.getAccessToken("wx384ee1648c99282d", "fc956c634d357b7d4908ddd49a416d04");
//        System.out.println("accessToken:"+accessToken);
//        url = "cgi-bin/user/get?access_token="+accessToken+"&next_openid=";
//
//        JSONObject jsonObject = WeChatUtil.httpAndHttpsRequest(baseUrl + url, "GET", "");
//
//
//        String string = jsonObject.toString();
//        System.out.println(string);
//
//        String next_openid = (String) jsonObject.get("next_openid");
//        JSONObject data = (JSONObject) jsonObject.get("data");
//        JSONArray openid = (JSONArray) data.get("openid");
//        for (int i = 0; i <openid.size() ; i++) {
//            String o = (String) openid.get(i);
//            JSONObject json = WeChatUtil.httpAndHttpsRequest(baseUrl + "cgi-bin/user/info?access_token="+accessToken+"&openid="+o+"&lang=zh_CN", "GET", "");
//            String oid = (String) json.get("openid");
//            String nickname = (String) json.get("nickname");
//                System.out.println(oid+":-----："+nickname);
//        }
//        jsonObject = WeChatUtil.httpAndHttpsRequest(baseUrl + url+next_openid, "GET", "");
//        string = jsonObject.toString();
//        System.out.println(string);
//
//        next_openid = (String) jsonObject.get("next_openid");
//        data = (JSONObject) jsonObject.get("data");
//        openid = (JSONArray) data.get("openid");
//        for (int i = 0; i <openid.size() ; i++) {
//            String o = (String) openid.get(i);
//            JSONObject json = WeChatUtil.httpAndHttpsRequest(baseUrl + "cgi-bin/user/info?access_token="+accessToken+"&openid="+o+"&lang=zh_CN", "GET", "");
//            String oid = (String) json.get("openid");
//            String nickname = (String) json.get("nickname");
//                System.out.println(oid+":-----："+nickname);
//        }
//        jsonObject = WeChatUtil.httpAndHttpsRequest(baseUrl + url+next_openid, "GET", "");
//        string = jsonObject.toString();
//        System.out.println(string);
//
//        next_openid = (String) jsonObject.get("next_openid");
//        data = (JSONObject) jsonObject.get("data");
//        openid = (JSONArray) data.get("openid");
//        for (int i = 0; i <openid.size() ; i++) {
//            String o = (String) openid.get(i);
//            JSONObject json = WeChatUtil.httpAndHttpsRequest(baseUrl + "cgi-bin/user/info?access_token="+accessToken+"&openid="+o+"&lang=zh_CN", "GET", "");
//            String oid = (String) json.get("openid");
//            String nickname = (String) json.get("nickname");
//                System.out.println(oid+":-----："+nickname);
//        }jsonObject = WeChatUtil.httpAndHttpsRequest(baseUrl + url+next_openid, "GET", "");
//        string = jsonObject.toString();
//        System.out.println(string);
//
//        next_openid = (String) jsonObject.get("next_openid");
//        data = (JSONObject) jsonObject.get("data");
//        openid = (JSONArray) data.get("openid");
//        for (int i = 0; i <openid.size() ; i++) {
//            String o = (String) openid.get(i);
//            JSONObject json = WeChatUtil.httpAndHttpsRequest(baseUrl + "cgi-bin/user/info?access_token="+accessToken+"&openid="+o+"&lang=zh_CN", "GET", "");
//            String oid = (String) json.get("openid");
//            String nickname = (String) json.get("nickname");
//                System.out.println(oid+":-----："+nickname);
//        }
//    }
//
//
//    @Test
//    public void uploadMedia() throws Exception {
//
//        StringBuffer buffer = new StringBuffer();
//        JSONObject jsonObject = null;
//
//        String url = "https://weibo.com/aj/mblog/add?ajwvr=6&__rnd="+System.currentTimeMillis();
//        WeiboLogin weiboLogin = new WeiboLogin();
//        weiboLogin.setUsername("16619884445");
//        weiboLogin.setPassword("wb.123456");
//        boolean login = weiboLogin.login();
//        System.out.println(login);
//        String cookie = weiboLogin.getCookie();
//        System.out.println(cookie);
//        HttpPost httpPost = new HttpPost(url);
//        httpPost.addHeader("Accept", "*/*");
//        httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
//        httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
//        httpPost.addHeader("Connection", "keep-alive");
//        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
//        /**
//         * 设置自己的cookie
//         */
//        httpPost.setHeader("Cookie",cookie);
//        httpPost.addHeader("Host", "weibo.com");
//        httpPost.addHeader("Origin", "https://weibo.com");
//        httpPost.addHeader("Referer", "https://weibo.com/u/5408400918/home?topnav=1&wvr=6");
//        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36");
//        httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
//
//        /**
//         * 设置参数
//         *
//         *
//         */
//        Map<String , Object> contextmap = new HashMap<>();
//        contextmap.put("location","v6_content_home");
//        contextmap.put("text","一爱仕达覅你回复士aasdf爱上对方互asdasd夫");
//        contextmap.put("style_type","1");
//        contextmap.put("isReEdit","false");
//        contextmap.put("rank","0");
////        contextmap.put("retcode","b8439be52c67b02c18bc744408c9b257");
//        contextmap.put("_t","0");
//        contextmap.put("module","stissue");
//        contextmap.put("pub_source","main_");
//        contextmap.put("pub_type","dialog");
//        contextmap.put("isPri","0");
//
//            org.apache.http.client.HttpClient httpClient = new SSLClient();
//            //设置参数
//            List<NameValuePair> list = new ArrayList<NameValuePair>();
//            Iterator<Map.Entry<String, Object>> iterator = contextmap.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<String, Object> elem = iterator.next();
//                list.add(new BasicNameValuePair(elem.getKey(), (String) elem.getValue()));
//            }
//            if (list.size() > 0) {
//                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
//                httpPost.setEntity(entity);
//            }
//            HttpResponse resp = httpClient.execute(httpPost);
//            System.out.println(resp);
//            HttpEntityWrapper entity = (HttpEntityWrapper) resp.getEntity();
//            InputStream inputStream = entity.getContent();
//        GZIPInputStream gin = new GZIPInputStream(inputStream);
//            InputStreamReader inputStreamReader = new InputStreamReader(gin, "gbk");
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String str = null;
//            while ((str = bufferedReader.readLine()) != null) {
//                System.out.println(str);
//                buffer.append(str);
//            }
//            bufferedReader.close();
//            inputStreamReader.close();
//            // 释放资源
//            inputStream.close();
//            inputStream = null;
////        System.out.println(buffer.toString());
//            jsonObject = JSONObject.fromObject(buffer.toString());
//        JSONObject data = (JSONObject) jsonObject.get("data");
//        String html = (String) data.get("html");
//        if (html != null) {
//            int i = html.lastIndexOf("mid=");
//            System.out.println(i);
//            String substring = html.substring(i + 4, i + 20);
//            System.out.println(substring);
//        }
//
////            System.out.println(jsonObject);
//            String reasonPhrase = resp.getStatusLine().getReasonPhrase();
////            System.out.println(reasonPhrase);
//
//
//        Date date = new Date(1540450852010L);
//        System.out.println(date);
//        str = "<div       \n" +
//                "tbinfo=\\\"ouid=5408400918\\\"  diss-data=\\\"group_source=\\\" class=\\\"WB_cardwrap WB_feed_type S_bg2 WB_feed_like\\\"  mid=\\\"4299033533528069\\\"  action-type=\\\"feed_list_item\\\"  >\\r\\n            \n" +
//                "<div class=\\\"WB_feed_detail clearfix\\\"  node-type=\\\"feed_content\\\"\n" +
//                "\n" +
//                "<div class=\\\"WB_screen W_fr\\\" node-type=\\\"fl_screen_box\\\">\n" +
//                "<div class=\\\"screen_box\\\">\n" +
//                "<a href=\\\"javascript:void(0);\\\" action-type=\\\"fl_menu\\\">\n" +
//                "<i class=\\\"W_ficon ficon_arrow_down S_ficon\\\">c<\\/i><\\/a>\n" +
//                "<div class=\\\"layer_menu_list\\\" node-type=\\\"fl_menu_right\\\" style=\\\"display:none; position: absolute; z-index: 999;\\\">\n" +
//                "<ul>                              \n" +
//                "<li>\n" +
//                "<a href=\\\"javascript:;\\\"  action-type=\\\"feed_list_delete\\\" title=\\\"删除\\\">删除<\\/a><\\/li>\n" +
//                "<li><a href=\\\"javascript:;\\\" action-type=\\\"fl_friendVisible\\\" action-data=\\\"visible=2\\\" title=\\\"转换为好友圈可见\\\">转换为好友圈可见<\\/a><\\/li>\n" +
//                "<li><a href=\\\"javascript:;\\\" action-type=\\\"fl_personalVisible\\\" action-data=\\\"visible=1\\\" title=\\\"转换为仅自己可见\\\">转换为仅自己可见<\\/a><\\/li>\n" +
//                "</ul>\n" +
//                "<ul style=\\\"display:none;\\\" node-type=\\\"hide\\\">\n" +
//                "</ul>\n" +
//                "</div>\n" +
//                "</div>\n" +
//                "</div>\n" +
//                "<div class=\\\"WB_face W_fl\\\">\\r\\n                <div class=\\\"face\\\"><a target=\\\"_blank\\\" class=\\\"W_face_radius\\\" suda-uatrack=\\\"key=feed_headnick&value=pubuser_head:4299033533528069\\\"  href=\\\"\\/u\\/5408400918?from=feed&loc=avatar\\\" title=\\\"花盆养蘑菇\\\"><img usercard=\\\"id=5408400918&refer_flag=\\\" title=\\\"花盆养蘑菇\\\" alt=\\\"\\\" width=\\\"50\\\" height=\\\"50\\\" src=\\\"\\/\\/tva4.sinaimg.cn\\/crop.0.0.512.512.180\\/005U168Sjw8f3kze0gh1oj30e80e80t2.jpg\\\" class=\\\"W_face_radius\\\" \\/><\\/a><\\/div>\\r\\n                        <\\/div>\\r\\n        <div class=\\\"WB_detail\\\">\\r\\n            <div class=\\\"WB_info\\\">\\r\\n                <a suda-uatrack=\\\"key=feed_headnick&value=pubuser_nick:4299033533528069\\\" target=\\\"_blank\\\"  class=\\\"W_f14 W_fb S_txt1\\\" nick-name=\\\"花盆养蘑菇\\\" title=\\\"花盆养蘑菇\\\" href=\\\"\\/u\\/5408400918?from=feed&loc=nickname\\\" title=\\\"花盆养蘑菇\\\" usercard=\\\"id=5408400918&refer_flag=\\\">花盆养蘑菇<\\/a><a target=\\\"_blank\\\" href=\\\"https:\\/\\/m.weibo.cn\\/z\\/panda\\\" title=\\\"熊猫守护者\\\"><i class=\\\"W_icon icon_panda\\\"><\\/i><\\/a>            <\\/div>\\r\\n            <div class=\\\"WB_from S_txt2\\\">\\r\\n                <!-- minzheng add part 2 -->\\r\\n                                                            <a target=\\\"_blank\\\" href=\\\"\\/5408400918\\/GFyjveNOl?ref=home\\\" title=\\\"2018-10-25 15:35\\\" date=\\\"1540452929000\\\" class=\\\"S_txt2\\\" node-type=\\\"feed_list_item_date\\\" suda-data=\\\"key=tblog_home_new&value=feed_time:4299033533528069\\\"> 10秒前<\\/a> 来自 <a class=\\\"S_txt2\\\" suda-data=\\\"key=tblog_home_new&value=feed_come_from\\\" action-type=\\\"app_source\\\" target=\\\"_blank\\\" href=\\\"http:\\/\\/app.weibo.com\\/t\\/feed\\/6vtZb0\\\" rel=\\\"nofollow\\\">微博 weibo.com<\\/a>                                                    <!-- minzheng add part 2 -->\\r\\n                            <\\/div>\\r\\n                        <div class=\\\"PCD_user_b S_bg1\\\" node-type=\\\"follow_recommend_box\\\" style=\\\"display:none\\\"><\\/div>\\r\\n            <div class=\\\"WB_text W_f14\\\" node-type=\\\"feed_list_content\\\" >\\r\\n                                                        111 \u200B\u200B\u200B\u200B                            <\\/div>\\r\\n                                                <div class=\\\"WB_expand_media_box \\\" style=\\\"display: none;\\\" node-type=\\\"feed_list_media_disp\\\"><\\/div>\\r\\n                                                                    <!-- 引用文件时，必须对midia_info赋值 -->\\n<!-- 微博心情，独立于标准的ul节点 -->\\n\\n                    <div class=\\\"WB_media_wrap clearfix\\\" node-type=\\\"feed_list_media_prev\\\">\\n        <div class=\\\"media_box\\\" >\\n                                                <!--图片个数等于1，只显示图片-->\\n                    \\t\\t\\t\\t\\t\\t\\t    <!--picture_count == 1-->\\n    <ul class=\\\"WB_media_a  WB_media_a_m1 clearfix\\\"  action-data=\\\"isPrivate=0&relation=0&clear_picSrc=//wx1.sinaimg.cn/mw690/005U168Sgy1fwkiai49h2j30be074dhs.jpg\\\">\\n                        <li  class=\\\"WB_pic li_1 S_bg1 S_line2 bigcursor \\\"  action-data=\\\"isPrivate=0&relation=0&pid=005U168Sgy1fwkiai49h2j30be074dhs&object_ids=1042018:61771d6648d2503659631c5d433b5e00&photo_tag_pids=&uid=5408400918&mid=4299033533528069&pic_ids=005U168Sgy1fwkiai49h2j30be074dhs&pic_objects=\\\" action-type=\\\"feed_list_media_img\\\" suda-uatrack=\\\"key=tblog_newimage_feed&value=image_feed_unfold:4299033533528069:005U168Sgy1fwkiai49h2j30be074dhs:5408400918\\\">\\n                             <img src=\\\"\\/\\/wx1.sinaimg.cn\\/orj360\\/005U168Sgy1fwkiai49h2j30be074dhs.jpg\\\">\\n            <i class=\\\"W_loading\\\" style=\\\"display:none;\\\"><\\/i>\\n                  \\n            <i class=\\\"W_loading\\\" style=\\\"display:none;\\\"><\\/i>\\n                    <\\/li>\\n    <\\/ul>\\n                                    <\\/div>\\n    <\\/div>\\n         \\n<!-- super topic card -->\\n                                                                                                                    <\\/div>\\r\\n        <div class=\\\"WB_like\\\" node-type=\\\"templeLike_ani\\\" action-data=\\\"parise_id=p_0000\\\" style=\\\"display:none;\\\">\\r\\n            <div class=\\\"anibox UI_ani\\\" style=\\\"background-image:url(\\/\\/img.t.sinajs.cn\\/t6\\/skin\\/public\\/like\\/p_0000_pc.png?version=201810251535);\\\"><\\/div>\\r\\n        <\\/div>\\r\\n    <\\/div>\\r\\n        <!-- minzheng add part 3 -->\\r\\n            <div class=\\\"WB_feed_handle\\\">\\r\\n            <div class=\\\"WB_handle\\\">\\r\\n                <ul class=\\\"WB_row_line WB_row_r4 clearfix S_line2\\\">\\r\\n                    <li>\\r\\n                                                                                    <a class=\\\"S_txt2\\\" href=\\\"javascript:void(0);\\\" action-type=\\\"fl_pop\\\" action-data=\\\"mid=4299033533528069&from=main_feed_pc_01\\\" suda-uatrack=\\\"key=tblog_fstt&value=pop_butt_home\\\"><span class=\\\"pos\\\"><span class=\\\"line S_line1\\\">推广<\\/span><\\/span><\\/a>\\r\\n                                                                        <\\/li>\\r\\n                                            <li>\\r\\n                            <a class=\\\"S_txt2\\\"  suda-data=\\\"key=smart_feed&value=time_sort_tran:4299033533528069\\\" href=\\\"javascript:void(0);\\\" action-history=\\\"rec=1\\\" action-type=\\\"fl_forward\\\" action-data=\\\"allowForward=1&url=https:\\/\\/weibo.com\\/5408400918\\/GFyjveNOl&mid=4299033533528069&name=花盆养蘑菇&uid=5408400918&domain=5408400918&pid=005U168Sgy1fwkiai49h2j30be074dhs\\\" ><span class=\\\"pos\\\"><span class=\\\"line S_line1\\\" node-type=\\\"forward_btn_text\\\"><span><em class=\\\"W_ficon ficon_forward S_ficon\\\">&#xe607;<\\/em><em>转发<\\/em><\\/span><\\/span><\\/span><\\/a>\\r\\n                        <\\/li>\\r\\n                                        <li>\\r\\n                                                <a class=\\\"S_txt2\\\" suda-data=\\\"key=smart_feed&value=time_sort_comm:4299033533528069\\\" href=\\\"javascript:void(0);\\\" action-type=\\\"fl_comment\\\" action-data=\\\"ouid=5408400918&location=home\\\"><span class=\\\"pos\\\"><span class=\\\"line S_line1\\\"  node-type=\\\"comment_btn_text\\\"><span><em class=\\\"W_ficon ficon_repeat S_ficon\\\">&#xe608;<\\/em><em>评论<\\/em><\\/span><\\/span><\\/span><\\/a>\\r\\n                        <span class=\\\"arrow\\\" style=\\\"display: none;\\\" node-type=\\\"cmtarrow\\\"><span class=\\\"W_arrow_bor W_arrow_bor_t\\\"><i class=\\\"S_line1\\\"><\\/i><em class=\\\"S_bg1_br\\\"><\\/em><\\/span><\\/span>\\r\\n                                                                <\\/li>\\r\\n                    <li class=\\\"\\\">\\r\\n                        <!--cuslike用于前端判断是否显示个性赞，1:显示 -->\\r\\n                        <a href=\\\"javascript:void(0);\\\" class=\\\"S_txt2\\\" action-type=\\\"fl_like\\\"  action-data=\\\"version=mini&qid=heart&mid=4299033533528069&like_src=1&cuslike=1\\\" title=\\\"赞\\\" ><span class=\\\"pos\\\"><span class=\\\"line S_line1\\\">\\r\\n                                                                                                                                                                                                                                    <span node-type=\\\"like_status\\\" class=\\\"\\\"><em class=\\\"W_ficon ficon_praised S_txt2\\\">ñ<\\/em><em>赞<\\/em><\\/span>                    <\\/span><\\/span><\\/a>\\r\\n                    <span class=\\\"arrow\\\" node-type=\\\"cmtarrow\\\"><span class=\\\"W_arrow_bor W_arrow_bor_t\\\"><i class=\\\"S_line1\\\"><\\/i><em class=\\\"S_bg1_br\\\"><\\/em><\\/span><\\/span>\\r\\n                    <\\/li>\\r\\n                <\\/ul>\\r\\n            <\\/div>\\r\\n        <\\/div>\\r\\n        <div node-type=\\\"feed_list_repeat\\\" class=\\\"WB_feed_repeat S_bg1\\\" style=\\\"display:none;\\\"><\\/div>\\r\\n            <\\/div>\\r\\n\\t    \\t    \\t\\t\\t\"\n";
//        int i = str.lastIndexOf("mid=");
//        System.out.println(i);
//        String substring = str.substring(i + 4, i + 20);
//        System.out.println(substring);
//
//    }
//
//    @Test
//    public void batchgetMaterial() throws IOException, ParseException {
//        AccessToken accessToken = AutoOAuth4Code.AccessTokenRefresh("16619884445", "wb.123456");
//        Map<String ,String> parm = new HashMap<>();
//        String accessToken1 = accessToken.getAccessToken();
//        String title = "ceshi";
//        String content= "neitong";
//        String cover = "http://218.246.5.130//webpic/W0201809/W020180906/W020180906512588056443.jpg";
//        String summary = "daoyu";
//        String text = "短微博内容";
//        parm.put("title",title);
//        parm.put("content",content);
//        parm.put("cover",cover);
//        parm.put("summary",summary);
//        parm.put("text",text);
//        parm.put("accessToken",accessToken1);
//        HttpClient httpClient = new HttpClient("https://api.weibo.com/proxy/article/publish.json", parm);
//        httpClient.post();
//        String content1 = httpClient.getContent();
//    }
//
//    @Test
//    public void uploadImg() {
//
//    }
//
//   @Test
//    public void send() {
//        List<WechatInfo> wechatInfoList = new ArrayList<>();
//        WechatInfo wechatInfo = new WechatInfo();
//        wechatInfo.setAuthor("kang");
//        wechatInfo.setCover("D:\\尚善吉祥\\593277516412350740.png");
//        wechatInfo.setContent("测试详情");
//        wechatInfo.setOriginallink("123");
//        wechatInfo.setShowcover("1");
//        wechatInfo.setTitle("123");
//        wechatInfo.setWechatabstract("123");
//        wechatInfoList.add(wechatInfo);
//        String send = WeChatUtil.send(wechatInfoList, new WechatAccount());
//        System.out.println(send);
//    }
//}