package com.vti.config.resourceproperties;  // cái đỏ này k cần qtam

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data // nó tự đọc
@ConfigurationProperties(prefix = "server") // h mình lấy cả 3 thawgn đáy vào đây qua 1 tiền tố thôi còn tên nó tự mapping
public class ServerProperty {   // mình cx dùng cái annotation na ná @Value
    private int port;

    private String hostName;
    private String protocol;

    public String getUrl() {
        return protocol + "://" + hostName + ":" + getPort();
    }
}
