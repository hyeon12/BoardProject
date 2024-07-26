package org.hyeon.global.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix="file.upload")
public class FileProperties {
    private String path; //file.upload.path
    private String url; //file.upload.url
}
