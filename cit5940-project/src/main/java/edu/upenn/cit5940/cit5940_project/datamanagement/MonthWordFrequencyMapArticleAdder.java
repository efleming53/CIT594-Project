package edu.upenn.cit5940.cit5940_project.datamanagement;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import edu.upenn.cit5940.cit5940_project.common.dto.Article;
import edu.upenn.cit5940.cit5940_project.common.dto.StopWords;
import edu.upenn.cit5940.cit5940_project.common.dto.Tokenizer;

public class MonthWordFrequencyMapArticleAdder implements ArticleAdder<Map<String, Map<String, Integer>>> {
	
	private static final MonthWordFrequencyMapArticleAdder INSTANCE = new MonthWordFrequencyMapArticleAdder();
	
	private MonthWordFrequencyMapArticleAdder() {};
	
	public static MonthWordFrequencyMapArticleAdder getInstance() {
		return INSTANCE;
	}
	
	// adds words in given article to monthWordFrequencyMap
	public void addArticle(Article article, Map<String, Map<String, Integer>> map) {
		
		// normalize article date to YYYY-MM
		LocalDate rawDate = article.getDate();
		String date = rawDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
		
		String[] titleTokens = Tokenizer.tokenize(article.getTitle());
		String[] bodyTokens = Tokenizer.tokenize(article.getBody());
		
		addArticleHelper(titleTokens, date, map);
		addArticleHelper(bodyTokens, date, map);
		
	}
	
	private static void addArticleHelper(String[] tokens, String date, Map<String, Map<String, Integer>> map) {
		
		// if map doesnt have given date, make a new entry with date and empty map of word frequencies
		if (!map.containsKey(date)) {
			Map<String, Integer> newFreqMap = new HashMap<>();
			map.put(date, newFreqMap);
		}
		
		Map<String, Integer> freqMap = map.get(date);
		
		for (String token : tokens) {
			
			if (StopWords.WORDS.contains(token)) {
				continue;
			}
			
			// if frequency map already has this word, replace it with incremented frequency
			if (freqMap.containsKey(token)) {
				Integer frequency = freqMap.get(token);
				freqMap.put(token, frequency + 1);
			
			// if word not in freqMap, put it there with starting frequency of 1
			} else {
				freqMap.put(token, 1);
			}
		}
	}

}
