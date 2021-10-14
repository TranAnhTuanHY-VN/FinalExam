package com.vti.config.resourceproperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "server")
public class ServerProperty {
    private int port;

    private String hostName;
    private String protocol;

    public String getUrl() {
        return protocol + "://" + hostName + ":" + getPort();
    }
}
