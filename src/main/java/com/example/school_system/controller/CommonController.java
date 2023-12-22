package com.example.school_system.controller;

import com.example.school_system.commom.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${school.path}")
    private String basePath;

    private String fileName;

    @PostMapping("/upload")
    public String upload(MultipartFile file) throws IOException {

        //获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        //截取后缀
        String suffix=originalFilename.substring(originalFilename.lastIndexOf("."));


        //使用UUID重新生成文件名，防止覆盖
        fileName = UUID.randomUUID().toString()+suffix;

        //file只是临时文件，需要转存到指定位置
        file.transferTo(new File(basePath+fileName));

        return fileName;
    }

    @GetMapping("/getURL")
    public String getURL(){
        return fileName;
    }

}
