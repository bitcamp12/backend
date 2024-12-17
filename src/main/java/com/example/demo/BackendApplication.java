package com.example.demo;

import java.io.File;
import java.io.IOException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan({"com.example.demo.dao"})
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
        
	}

}
