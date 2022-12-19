package com.meicai.langcheck.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "langcheck")
public class LangcheckProperties {
	
    private String uploadDir;
    
    private String sentenceModelPath;
    
    private String tokenizerModelPath;
    
    private String wordFreqPath;

    public String getSentenceModelPath() {
		return sentenceModelPath;
	}

	public void setSentenceModelPath(String sentenceModelPath) {
		this.sentenceModelPath = sentenceModelPath;
	}

	public String getTokenizerModelPath() {
		return tokenizerModelPath;
	}

	public void setTokenizerModelPath(String tokenizerModelPath) {
		this.tokenizerModelPath = tokenizerModelPath;
	}

	public String getWordFreqPath() {
		return wordFreqPath;
	}

	public void setWordFreqPath(String wordFreqPath) {
		this.wordFreqPath = wordFreqPath;
	}

	public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }      

}
