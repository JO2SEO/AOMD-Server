package jo2seo.aomd.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String saveProfileImage(MultipartFile file) throws IOException;
    String downloadKakaoProfileImage(String profileImgUrl) throws IOException;
}
