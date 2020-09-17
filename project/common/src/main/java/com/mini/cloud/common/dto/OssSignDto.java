package com.mini.cloud.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OssSignDto implements Serializable {
    private static final long serialVersionUID = -7031983685253606917L;

    private String date;

    private String ossSign;

    private String contentType;

    private String contentLength;

    private String realUrl;

    private String resource;
}
