package com.vti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.vti"})
public class GroupManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroupManagementApplication.class, args);
    }

}
