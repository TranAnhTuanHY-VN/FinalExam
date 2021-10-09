package com.vti.controller;

import com.vti.service.IFileService;
import com.vti.utils.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/api/v1/files")
public class FileController {
    @Autowired
    private IFileService fileService;

    @PostMapping(value = "/image")  // có thể thêm id vào Request param này cx đc logic k khó lắm oke a để e thử làm
    public ResponseEntity<?> upLoadImage(@RequestParam(name = "image") MultipartFile image) throws IOException, URISyntaxException {
        // lm thử xem a cx chưa lm mà test postman ngon là ok r
        //Vâng...mà cái reset pasword  thì khó k a nhỉ
        // a chưa lm mà a nghĩ nó cx i sì cái regis
        //Vâng a....thế để em xen lại code rồi e làm thêm ạ
        // ok cứ đọc logic đi chưa cần lm FE đâu
        //Vâng ạ thôi a out nhé kVhaasna lgok
        if (!new FileManager().isTypeFileImage(image)) {
            return new ResponseEntity<>("File must be image!", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        // tí nó lưu vào documents
        // code i sì record
        return new ResponseEntity<>(fileService.uploadImage(image), HttpStatus.OK);
    }

    @GetMapping(value = "/image")
    public ResponseEntity<?> downloadImage(@RequestParam String nameImage) throws IOException {

        // TODO validate

        File imageFile = fileService.downloadImage(nameImage);
        InputStreamResource imageStream = new InputStreamResource(new FileInputStream(imageFile));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", nameImage));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(imageFile.length())
                .contentType(MediaType.parseMediaType("application/txt"))
                .body(imageStream);
    }
}
