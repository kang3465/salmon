package cn.ele.core.controller;

import cn.ele.core.entity.Result;
import cn.ele.core.util.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("upload")
public class UploadController {

    @Value("${FILE_SERVER_URL}")
    private String fileServer;
    @Value("${picpath}")
    private String picpath;
    @Value("${filepath}")
    private String filepath;

    @RequestMapping("uploadFilefastDFS")
    @ResponseBody
    public Result uploadFilefastDFS(MultipartFile file) {
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
    @ResponseBody
    @RequestMapping("uploadFile")
    public Result uploadFile(MultipartFile file) {
        String fileName = "";
        try {
            System.out.println(file);
            byte[] bytes = file.getBytes();
            String originalFilename = file.getOriginalFilename();
            long size = file.getSize();
            InputStream inputStream = file.getInputStream();
            String name = file.getName();
            boolean empty = file.isEmpty();
            String contentType = file.getContentType();
            fileName=new Date().getTime()+originalFilename;
            file.transferTo(new File(filepath+"/"+fileName));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "上传失败!");
        }
        return new Result(true, fileName);
    }

    @RequestMapping("readFile")
    public ResponseEntity<byte[]> readFile(String fileName) {
        ByteArrayOutputStream bos=null;
        BufferedInputStream in=null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        byte[] fileByte = new byte[0];
        try {

            File file = new File(filepath + "/" + fileName);
            headers.setContentDispositionFormData("attachment", file.getName());
            FileInputStream fis = new FileInputStream(file);

            bos = new ByteArrayOutputStream((int) file.length());
            byte[] b = new byte[1024];
            int len = -1;
            while((len = fis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            fileByte = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(fileByte, headers, HttpStatus.OK);
    }


}
