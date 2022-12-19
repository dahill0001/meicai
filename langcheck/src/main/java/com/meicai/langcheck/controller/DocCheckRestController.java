package com.meicai.langcheck.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.meicai.langcheck.exception.FileStorageException;
import com.meicai.langcheck.payload.DocCheckResponse;
import com.meicai.langcheck.service.DocCheckService;
import com.meicai.langcheck.service.FileStorageService;

@RestController
public class DocCheckRestController {



    private static final Logger logger = LoggerFactory.getLogger(DocCheckController.class);

    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
	private DocCheckService docCheckService;


    @PostMapping("/fileScore")
    public DocCheckResponse checkFileScore(@RequestParam("file") MultipartFile file) {

        // check if file is empty
        if (file.isEmpty()) {
            throw new FileStorageException("Please select a file to upload.");
        }

       
        String originalFileName = file.getOriginalFilename();
        int dotIndex = originalFileName.lastIndexOf('.');
        String fileType =  (dotIndex == -1) ? "" : originalFileName.substring(dotIndex + 1);
        if ("txt".equalsIgnoreCase(fileType)) {
            String fileName = fileStorageService.storeFile(file);
            logger.info(originalFileName + ": fileName" + fileName);
            String content = fileStorageService.loadFileAsString(fileName);
            String fleshScore = docCheckService.checkFleschScore(content, fileName);
            logger.info(originalFileName + ": fleshScore" + fleshScore);
            String wfScore = docCheckService.checkWordFrequenciesScore(content, fileName);
            logger.info(originalFileName + ": wfScore" + wfScore);
            fileStorageService.removeFile(fileName);
            // return success response

            return new DocCheckResponse(originalFileName, fleshScore, wfScore);
            
            
        	
        } else {
        	logger.info(originalFileName + "File type must be txt!");
        	throw new FileStorageException("File type must be txt!");
        }
               
    }

}
