package com.vti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication()    // cái này nó tự scan nên hiểu Bean, mình dùng hàm main này scan
// scan chủ yếu ở file này còn cấu hình đâu cx đc
@ConfigurationPropertiesScan("com.vti") // nhìn cái tên giống file kia đúg k phải cho spring scan
public class GroupManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroupManagementApplication.class, args);
    }
    // cấu hình đây cx đc mà k nên
}
