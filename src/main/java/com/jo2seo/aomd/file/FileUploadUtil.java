package com.jo2seo.aomd.file;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUploadUtil {
    public static String saveProfileImage(MultipartFile file, String saveFolderUrl) throws IOException {
        String sourceFileName = file.getOriginalFilename();
        String sourceFileExtension = StringUtils.getFilenameExtension(sourceFileName).toLowerCase();
        String destinationFileName = UUID.randomUUID() + "." + sourceFileExtension;
        File destinationFile = new File(saveFolderUrl + "/" + destinationFileName);
        destinationFile.getParentFile().mkdirs();
        file.transferTo(destinationFile);
        return destinationFile.getPath();
    }
}
