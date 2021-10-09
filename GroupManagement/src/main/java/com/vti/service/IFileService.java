package com.vti.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public interface IFileService {
    String uploadImage(MultipartFile image) throws IOException, URISyntaxException;

    File downloadImage(String nameImage) throws IOException;
}
