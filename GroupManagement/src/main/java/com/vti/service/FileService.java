package com.vti.service;

import com.vti.utils.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Objects;

@Service
public class FileService implements IFileService{
    @Autowired
    private FileManager fileManager;

    @Value("${folder.resource}/documents")
    private String documentFolder;


    @Override
    public String uploadImage(MultipartFile image) throws IOException, URISyntaxException {
        String absolutePathFolder = new File(documentFolder).getAbsolutePath();
        String nameImage = new Date().getTime() + "." + fileManager.getFormatFile(Objects.requireNonNull(image.getOriginalFilename()));

        String path = absolutePathFolder + "\\" + nameImage;

        fileManager.createNewMultiPartFile(path, image);

        // TODO save link file to database


        // return link uploaded file
        return path;
    }

    @Override
    public File downloadImage(String nameImage) throws IOException {
        String path = documentFolder + "/" + nameImage;

        return new File(path);
    }
}
