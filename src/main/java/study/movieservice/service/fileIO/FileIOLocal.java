package study.movieservice.service.fileIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@Service
public class FileIOLocal implements FileIO {

    private final String fixedPath;

    public FileIOLocal(@Value("${fixedPath}") String fixedPath) {
        this.fixedPath = fixedPath;
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {

        String originalFileName = file.getOriginalFilename();
        String FilePath = fixedPath + originalFileName;
        File destination = new File(FilePath);

        file.transferTo(destination);

        return FilePath;
    }
}