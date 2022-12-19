package com.meicai.langcheck.payload;

public class DocCheckResponse {
	private String fileName;
	private String fleshScore;
	private String wfScore;

	public DocCheckResponse(String fileName, String fleshScore, String wfScore) {
		super();
		this.fileName = fileName;
		this.fleshScore = fleshScore;
		this.wfScore = wfScore;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFleshScore() {
		return fleshScore;
	}

	public void setFleshScore(String fleshScore) {
		this.fleshScore = fleshScore;
	}

	public String getWfScore() {
		return wfScore;
	}

	public void setWfScore(String wfScore) {
		this.wfScore = wfScore;
	}

}
