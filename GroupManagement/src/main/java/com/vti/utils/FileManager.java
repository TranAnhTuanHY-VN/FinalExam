package com.vti.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component  // code này trong record ấy nhé a chỉ sửa tí bên service thôi
// cứ component là autowired đc
public class FileManager {

    public boolean isFileOrFolderExist(String path) throws IOException {
        return new File(path).exists();
    }

    public boolean isTypeFileImage(MultipartFile file) {
        return file.getContentType().toLowerCase().contains("image");
    }

    public void createNewMultiPartFile(String path, MultipartFile multipartFile)
            throws IllegalStateException, IOException {
        // write file
        File file = new File(path);
        multipartFile.transferTo(file); // cái này nó ghi file theo đg dẫn tuyệt đối
        // mà mình chỉ có đg dẫn tg đổi của folder thôi nên phải tìm đg dẫn tuyệt đối folder trc
        // r ms ghép file ảnh thành đg đãn ảnh đc
        // hoàn thành rđấy còn cái refresh token e cần luôn  k
        //CHắc cũng chưa càn luôn đâu a...có gì em mần sáng mai r tính ạ
        //Cái dow, up file này để thay đổi avatar afg a thì mình viết cái này sang bên service của account nx
    }

    public String getFormatFile(String input) {
        String[] results = input.split("\\.");
        return results[results.length - 1];
    }
}
