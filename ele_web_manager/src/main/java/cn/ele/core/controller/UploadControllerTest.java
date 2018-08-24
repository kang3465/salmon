package cn.ele.core.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/** cn.ele.core.controller
       
        name of the package in which the new file is created
        fjnet
         
        current user system login name
        2018/8/18
         
        current system date
        10:14
         
        current system time
        2018
         
        current year
        08
         
        current month
        八月
         
        first 3 letters of the current month name.Example:Jan,Feb,etc.
        八月
         
        full name of the current month.Example:January,February,etc.
        18
         
        current day of the month
        星期六
         
        first 3 letters of the current day name.Example:Mon,Tue,etc.
        星期六
         
        full name of the current day.Example:Monday,Tuesday,etc.
        10
         
        current hour
        14
         
        current minute
        element_parent
         
        the name of the current projectpublic
*/
class UploadControllerTest {

    @Test
    public void uploadFile() throws IOException {
        File file = new File("./json/nav_main.json");
        FileReader fileReader=new FileReader(file);
        StringBuffer sb = new StringBuffer();
        char[] ch = new char[1024];
        int len = -1;
        while((len = fileReader.read(ch))!=-1) {
            sb.append(ch);
        }
        String s = sb.toString();
        System.out.println(s);
        JSONObject jsonObject = JSON.parseObject(s);
        JSONArray data = (JSONArray) jsonObject.get("data");

        for (int i = 0; i <data.size() ; i++) {
            JSONObject o = (JSONObject) data.get(i);
            String text = (String) o.get("text");
            String icon = (String) o.get("icon");
            JSONArray subset = (JSONArray) o.get("subset");
            if (subset!=null){

            }

        }
    }

    private boolean saveMenu(JSONArray data){

        for (int i = 0; i <data.size() ; i++) {
            JSONObject o = (JSONObject) data.get(i);
            String text = (String) o.get("text");
            String icon = (String) o.get("icon");
            JSONArray subset = (JSONArray) o.get("subset");
            if (subset!=null){
                return saveMenu(subset);
            }

        }
        return true;
    }
}