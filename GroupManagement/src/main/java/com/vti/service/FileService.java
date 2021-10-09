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
    // cần đưa cái đg dẫn lưu vào đây thì mình cx cho ra config luôn như code trong record chưa cho ra a cải tiến r
    @Autowired
    private FileManager fileManager;    // a nhúng nó vào cho tiện đỡ phải new()

    // h cx phải lấy ra đúg k lại dùng
    @Value("${folder.resource}/documents")  // cái đg đẫn tuong đối nãy mình ghép thêm folder của mình avfo
    private String documentFolder;  //  / này là đg dẫn thôi nó thành string /documents nx


    @Override
    public String uploadImage(MultipartFile image) throws IOException, URISyntaxException {
        String absolutePathFolder = new File(documentFolder).getAbsolutePath(); // dòng này
        // getAbsolutePath absolute là tueetj đối nhé , tg đổi là mình k thích hard code thôi
        // linh hoạt hơn
        String nameImage = new Date().getTime() + "." + fileManager.getFormatFile(Objects.requireNonNull(image.getOriginalFilename()));

        String path = absolutePathFolder + "\\" + nameImage;

        fileManager.createNewMultiPartFile(path, image);
        // cái này vaaxan là code trên record thôi chỉ có đổi tên thành kiểu ngày h đỡ trùng
        // a thêm 1 dòng nx là tìm đg đẫn tuyệt đối của cái đg dẫn tg đối kia
        // TODO save link file to database  // thêm vào db đoạn này này
        // trên frontend mình chọn vào trang account là nó có ID trên localstorage r
        // mình up thì gửi thêm id vào đây nx dùng lệnh save của account service ấy
        // findById trc r setter cái link ảnh vào
        // save lại là ok
        // a thì chưa lm ms lm reggis thôi mà logic cx đễ

        // return link uploaded file
        return path;
    }

    @Override
    public File downloadImage(String nameImage) throws IOException {
        String path = documentFolder + "/" + nameImage;

        return new File(path);  // cái này a chưa lm tự ktra cx đc
    }
}
