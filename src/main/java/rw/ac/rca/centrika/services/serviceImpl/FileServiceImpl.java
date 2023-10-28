package rw.ac.rca.centrika.services.serviceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.rca.centrika.services.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    @Value("D:\\SpringImages")
    private String uploadDir;

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Path.of(uploadDir + File.separator + fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    public String updateFile(String oldFileName, MultipartFile newFile) throws IOException {
        deleteFile(oldFileName);
        return uploadFile(newFile);
    }

    public void deleteFile(String fileName) throws IOException {
        Path filePath = Path.of(uploadDir + File.separator + fileName);
        Files.deleteIfExists(filePath);
    }

    @Override
    public File getFile(String fileName) throws IOException {
        Path filePath = Path.of(uploadDir + File.separator + fileName);
        File file = filePath.toFile();

        if (file.exists()) {
            return file;
        } else {
            throw new IOException("File not found: " + fileName);
        }
    }
}
