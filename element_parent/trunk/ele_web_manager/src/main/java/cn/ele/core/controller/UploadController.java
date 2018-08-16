package cn.ele.core.controller;

import cn.ele.core.pojo.entity.Result;
import cn.ele.core.util.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Value("${FILE_SERVER_URL}")
    private String fileServer;
    @RequestMapping("uploadFile")
    public Result uploadFile(MultipartFile file){
        try {
            FastDFSClient fastDfsUtil = new FastDFSClient("classpath:fastDFS/fdfs_client.conf");
            //上传, 并返回保存的文件路径和名称
            //例如: group1/M00/00/01/wKjIgFsRCu-AF4C4AAvqH_kipG8254.jpg
            String path = fastDfsUtil.uploadFile(file.getBytes(), file.getOriginalFilename(), file.getSize());
            return new Result(true, fileServer + path);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "上传失败!");
        }

    }



}
