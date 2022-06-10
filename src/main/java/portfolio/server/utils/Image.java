package portfolio.server.utils;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Image {
    public static Path saveFile(MultipartFile imageFile) throws IOException {
        try {
            Path temp = Files.createTempFile("temp", ".jpg");
            byte[] bytes = imageFile.getBytes();
            Files.write(temp, bytes);

            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void saveAtBucket(String token, Path path) {
        String bucket_name = "erik-na-portfolio";
        String file_path = path.toString();

        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        try {
            s3.putObject(bucket_name, token+".jpg", new File(file_path));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }
}