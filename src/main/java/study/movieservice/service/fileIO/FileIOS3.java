package study.movieservice.service.fileIO;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class FileIOS3 implements FileIO {

    private final String bucket;
    private final String accessKey;
    private final String secretKey;
    private final String region;

    public FileIOS3(@Value("${cloud.aws.s3.bucket}") String bucket,
                    @Value("${cloud.aws.credentials.accessKey}") String accessKey,
                    @Value("${cloud.aws.credentials.secretKey}") String secretKey,
                    @Value("${cloud.aws.region.static}") String region
                    ) {
        this.bucket = bucket;
        this.accessKey=accessKey;
        this.secretKey=secretKey;
        this.region=region;
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {

        String originalFileName = file.getOriginalFilename();
        AmazonS3 s3Client=getS3Client();
        ObjectMetadata objectMetadata=new ObjectMetadata();
        ByteArrayInputStream byteArrayInputStream=getByteArrayInputStream(file,objectMetadata);

        s3Client.putObject(new PutObjectRequest(bucket,originalFileName,byteArrayInputStream,objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return s3Client.getUrl(bucket,originalFileName).toString();
    }

    private ByteArrayInputStream getByteArrayInputStream(MultipartFile file, ObjectMetadata objectMetadata) throws IOException {

        byte[] bytes = IOUtils.toByteArray(file.getInputStream());
        file.getInputStream().close();
        objectMetadata.setContentLength(bytes.length);

        return new ByteArrayInputStream(bytes);
    }

    public AmazonS3 getS3Client(){

        AWSCredentials credentials=new BasicAWSCredentials(accessKey,secretKey);

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }
}
