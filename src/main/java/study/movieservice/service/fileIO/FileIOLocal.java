package study.movieservice.service.fileIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import static study.movieservice.domain.ExceptionMessageConst.FAILED_FILE_RECEIVE;

@Component
public class FileIOLocal implements FileIO {

    private final String fixedPath;

    public FileIOLocal(@Value("${fixedPath}") String fixedPath) {
        this.fixedPath = fixedPath;
    }

    @Override
    public String saveFile(MultipartFile file) {

        String originalFileName=file.getOriginalFilename();
        String FilePath=fixedPath+originalFileName;
        File destination=new File(FilePath);

        try {
            file.transferTo(destination);
        } catch (IOException e) {
            throw new IllegalArgumentException(FAILED_FILE_RECEIVE.getMessage());
        }
        return FilePath;
    }
}
