package org.example.filemanager;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageService {

    //! Modify according to your local environment
    public static final String STORAGE_DIRECTORY = "C:\\Users\\kk000000\\OneDrive - Reed Elsevier Group ICO Reed Elsevier Inc\\Documents\\Project\\storage";

    public void save(MultipartFile fileToSave) throws IOException {
        // The file is going to be stored in the storage directory/folder
        if (fileToSave == null) {
            throw new NullPointerException("The file to save is null");
        }
        var targetFile = new File(STORAGE_DIRECTORY + File.separator + fileToSave.getOriginalFilename());
        if(!Objects.equals(targetFile.getParent(), STORAGE_DIRECTORY)) {
            throw new SecurityException("Unsupported file name");
        }
        Files.copy(fileToSave.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public File getDownloadFile(String fileName) throws FileNotFoundException {
        if (fileName == null) {
            throw new NullPointerException("The file name is null");
        }
        var fileToDownload = new File(STORAGE_DIRECTORY + File.separator + fileName);
        if(!Objects.equals(fileToDownload.getParent(), STORAGE_DIRECTORY)) {
            throw new SecurityException("Unsupported file name");
        }
        if (!fileToDownload.exists()) {
            throw new FileNotFoundException("No file named: " + fileName);
        }
        return fileToDownload;
    }
}
