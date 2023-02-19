package study.movieservice.service.fileIO;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileIO {
    String uploadFile(MultipartFile file)throws IOException;
}
