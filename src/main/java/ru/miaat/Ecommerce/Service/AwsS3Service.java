package ru.miaat.Ecommerce.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.authenticator.BasicAuthenticator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class AwsS3Service {
    private final String bucketName = "ecommerce-images-bucket";

    @Value("${aws.s3.access}")
    private String awsS3AccessKey;

    @Value("${aws.s3.secret}")
    private String awsS3SecretKey;

    public String saveImageToS3(MultipartFile photo) {
        try {
            // create a s3 credentials using access and secretKey
            String fileName = photo.getOriginalFilename();
            BasicAWSCredentials credentials = new BasicAWSCredentials(awsS3AccessKey, awsS3SecretKey);

            // create an s3 client with config credentials and region
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(Regions.EU_NORTH_1)
                    .build();

            // get input stream from photo
            InputStream inputStream = photo.getInputStream();

            // set metadata for the object
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/jpeg");

            // create a put-request to upload an image to s3
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata);
            s3Client.putObject(request);

            // getting an saved file URL
            return "https://" + bucketName + "s3.eu-north-1.amazonaws.com" + "/" + fileName;

        } catch (IOException e){
            log.error(e.getMessage());
            throw new RuntimeException("Unable to upload photo to AWS S3 Bucket: " + e.getMessage());
        }
    }
}
