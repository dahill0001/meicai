package com.meicai.langcheck.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meicai.langcheck.controller.DocCheckController;
import com.meicai.langcheck.exception.CommonException;
import com.meicai.langcheck.property.LangcheckProperties;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

@Service
public class DocCheckService {
	
	private static final Logger logger = LoggerFactory.getLogger(DocCheckController.class);

	SentenceDetectorME sdetector;
	TokenizerME tokenizer;
	Map<String, Long> wordFreqMap = new HashMap<String, Long>();

	private static final DecimalFormat df = new DecimalFormat("0.00");

	@Autowired
	public DocCheckService(LangcheckProperties langcheckProperties) {

		try {
			InputStream smIs = getClass().getResourceAsStream(langcheckProperties.getSentenceModelPath());
			SentenceModel sModel = new SentenceModel(smIs);
			sdetector = new SentenceDetectorME(sModel);
			logger.info(langcheckProperties.getSentenceModelPath() + ":Init SentenceDetectorME done.");

			InputStream tmIs = getClass().getResourceAsStream(langcheckProperties.getTokenizerModelPath());
			TokenizerModel tModel = new TokenizerModel(tmIs);
			tokenizer = new TokenizerME(tModel);
			logger.info(langcheckProperties.getTokenizerModelPath() + ":Init TokenizerModel done.");

			InputStream ufIs = getClass().getResourceAsStream(langcheckProperties.getWordFreqPath());

			CSVReader reader = new CSVReaderBuilder(new InputStreamReader(ufIs)).build();

			String[] nextLine;

			while ((nextLine = reader.readNext()) != null) {
				// nextLine[] is an array of values from the line
				// System.out.println(nextLine[0] + nextLine[1] + "etc...");
				Long freq = 0L;
				if ("count".equals(nextLine[1]) ) {
					continue;
				}
				try {
					freq = Long.valueOf(nextLine[1]);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

				wordFreqMap.put(nextLine[0], freq);

	
			}
			logger.info(langcheckProperties.getWordFreqPath() + ":Init wordFreqMap done.");

			df.setRoundingMode(RoundingMode.UP);

		} catch (Exception ex) {
			throw new CommonException("Could not init Doc check Servcie.", ex);
		}
	}

	/**
	 * @param paragraph
	 * @return The Flesch–Kincaid readability tests are readability tests designed
	 *         to indicate how difficult a passage in English is to understand
	 *         https://en.wikipedia.org/wiki/Flesch%E2%80%93Kincaid_readability_tests
	 */
	public String checkFleschScore(String paragraph, String fileName) {

		logger.info("Start sentence detect..." + fileName);
		String[] sentences = sdetector.sentDetect(paragraph);
		logger.info("Complete sentence detect" + fileName);
		logger.info("Start word tokenize..." + fileName);
		String[] tokens = tokenizer.tokenize(paragraph);
		logger.info("Compete word tokenize" + fileName);
		long totalSentences = sentences.length;
		long totalWords = tokens.length;
		long totalSyllables = 0;

		totalSyllables = Arrays.stream(tokens).mapToLong(this::countSyllables).sum();
		if (totalSentences != 0 && totalWords != 0) {
			double score = 206.835 - 1.015 * ((double) totalWords / totalSentences) - 84.6 * ((double) totalSyllables / totalWords);
			return df.format(score);
		} else {
			return "Invalid Format";
		}



	}

	/**
	 * @param paragraph
	 * @return Count the word frequencies for score
	 *
	 */
	public String checkWordFrequenciesScore(String paragraph, String fileName) {

		logger.info("Start word tokenize..." + fileName);
		String[] tokens = tokenizer.tokenize(paragraph);
		logger.info("Compete word tokenize" + fileName);
		long level1 = 0;
		long level2 = 0;
		long level3 = 0;
		long level4 = 0;
		long level5 = 0;
		long level6 = 0;

		for (String word : tokens) {

			Long freq = wordFreqMap.get(word.toLowerCase());

			if (Objects.nonNull(freq) && freq > 25000000) {
				level1++;
			} else if (Objects.nonNull(freq) && freq > 10000000) {
				level2++;
			} else if (Objects.nonNull(freq) && freq > 5000000) {
				level3++;
			} else if (Objects.nonNull(freq) && freq > 1500000) {
				level4++;
			} else if (Objects.nonNull(freq) && freq > 300000) {
				level5++;
			} else if (Objects.nonNull(freq)) {
				level6++;
			} else {
				continue;
			}

		}
		long total = level1 + level2 + level3 + level4 + level5 + level6;
		if (total != 0) {
			double score = (double) level1 / total * 60 + (double) level2 / total * 30 + (double) level3 / total * 20 + (double) level4 / total * 10;
			return df.format(score);
		} else {
			return "Invalid Format";
		}



	}

	public long countSyllables(String s) {
		int counter = 0;
		s = s.toLowerCase(); // converting all string to lowercase
		if (s.contains("the ")) {
			counter++;
		}
		String[] split = s.split("e!$|e[?]$|e,|e |e[),]|e$");

		ArrayList<String> al = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile("[aeiouy]+");

		for (int i = 0; i < split.length; i++) {
			String s1 = split[i];
			Matcher m = tokSplitter.matcher(s1);

			while (m.find()) {
				al.add(m.group());
			}
		}

		counter += al.size();
		return counter;
	}

}
