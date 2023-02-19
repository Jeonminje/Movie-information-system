package study.movieservice.service.fileIO;

import org.springframework.web.multipart.MultipartFile;

public interface FileIO {
    String saveFile(MultipartFile file);
}
