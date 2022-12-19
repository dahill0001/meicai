package com.meicai.langcheck.service;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.meicai.langcheck.exception.FileStorageException;
import com.meicai.langcheck.exception.MyFileNotFoundException;
import com.meicai.langcheck.property.LangcheckProperties;

@Service
public class FileStorageService {
	
	private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(LangcheckProperties langcheckProperties) {
        this.fileStorageLocation = Paths.get(langcheckProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
            logger.info(this.fileStorageLocation.getFileName() + ":Init fileStorageLocation done.");
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Copy File " + file.getOriginalFilename() + "to " + targetLocation.getFileName());

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    
    public String storeFileById(MultipartFile file) {
        // Normalize file name
        String fileName = UUID.randomUUID().toString();;

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Copy File " + file.getOriginalFilename() + "to " + targetLocation.getFileName());

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    public String removeFile(String fileName) {

        try {

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.delete(targetLocation);
            logger.info("Delete File: " + targetLocation.getFileName());

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not delte file " + fileName, ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
    
    public String loadFileAsString(String fileName) {
    	String content = "";
    	Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
    	boolean readSuccess = false;
        try {
            
            content = Files.readString(filePath);
            readSuccess = true;
            
        } catch (MalformedInputException ex) {
        	logger.info("Fail to read file by UTF 8, try ISO_8859_1");
        }  catch (Exception ex) {
            throw new FileStorageException("File not found " + fileName, ex);
        } 

        if (! readSuccess) {
            try {
                
                content = Files.readString(filePath, StandardCharsets.ISO_8859_1);
                readSuccess = true;
                
            } catch (MalformedInputException ex) {
            	logger.info("Fail to read file by ISO_8859_1, try US_ASCII");
            }  catch (Exception ex) {
                throw new FileStorageException("File not found " + fileName, ex);
            } 
        }
        if (! readSuccess) {
            try {
                
                content = Files.readString(filePath, StandardCharsets.US_ASCII);
                readSuccess = true;
                
            } catch (MalformedInputException ex) {
            	logger.info("Fail to read file by US_ASCII, try UTF_16");
            }  catch (Exception ex) {
                throw new FileStorageException("File not found " + fileName, ex);
            } 
        }
        if (! readSuccess) {
            try {
                
                content = Files.readString(filePath, StandardCharsets.UTF_16);
                readSuccess = true;
                
            } catch (MalformedInputException ex) {
            	logger.info("Fail to read file by UTF_16");
            }  catch (Exception ex) {
                throw new FileStorageException("File not found " + fileName, ex);
            } 
        }


        return content;
    }
}
