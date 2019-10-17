package com.comdegym.managerproductproject.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class UploadImage implements Serializable {
    private static final long serialVersionUID = 1L;
    private MultipartFile multipartFile;

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
