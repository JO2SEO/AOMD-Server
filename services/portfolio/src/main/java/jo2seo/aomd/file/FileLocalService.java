package jo2seo.aomd.file;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Service
public class FileLocalService implements FileService {
    public static final String PROJECT_DIR = System.getProperty("user.dir");
    public static final String PROFILE_IMG_DIR = "profileImg";
    @Override
    public String saveProfileImage(MultipartFile file) throws IOException {
        final String destinationDir = PROJECT_DIR + "/" + PROFILE_IMG_DIR;

        String sourceFileName = file.getOriginalFilename();
        String sourceFileExtension = StringUtils.getFilenameExtension(sourceFileName).toLowerCase();

        String destinationFileName = UUID.randomUUID() + "." + sourceFileExtension;

        File destinationFile = new File(destinationDir + "/" + destinationFileName);
        destinationFile.getParentFile().mkdirs();
        file.transferTo(destinationFile);

        return destinationFile.getPath();
    }

    @Override
    public String downloadKakaoProfileImage(String profileImgUrl) throws IOException {
        final String destinationDir = PROJECT_DIR + "/" + PROFILE_IMG_DIR;

        String sourceFileExtension = StringUtils.getFilenameExtension(profileImgUrl).toLowerCase();

        String destinationFileName = UUID.randomUUID() + "." + sourceFileExtension;

        File destinationFile = new File(destinationDir + "/" + destinationFileName);
        destinationFile.getParentFile().mkdirs();

        URL url = new URL(profileImgUrl);
        BufferedImage image = ImageIO.read(url);
        ImageIO.write(image, sourceFileExtension, destinationFile);

        return destinationFile.getPath();
    }
}
