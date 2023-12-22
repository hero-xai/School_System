package com.example.school_system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.util.AntPathMatcher;

@Slf4j
@SpringBootApplication
@ServletComponentScan
public class SchoolSystemApplication {
    public static void main(String[] args) {

        SpringApplication.run(SchoolSystemApplication.class, args);
        log.info("项目启动成功");
    }

}
