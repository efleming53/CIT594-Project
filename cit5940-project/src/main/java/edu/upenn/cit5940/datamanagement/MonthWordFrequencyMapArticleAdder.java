package edu.upenn.cit5940.datamanagement;

import java.util.*;
import edu.upenn.cit5940.common.dto.*;
import java.time.YearMonth;

// adds articles to map in support of topics and trends operations
public class MonthWordFrequencyMapArticleAdder implements ArticleAdder<Map<YearMonth, Map<String, Integer>>> {
	
	private static final MonthWordFrequencyMapArticleAdder INSTANCE = new MonthWordFrequencyMapArticleAdder();
	
	private MonthWordFrequencyMapArticleAdder() {};
	
	public static MonthWordFrequencyMapArticleAdder getInstance() {
		return INSTANCE;
	}
	
	// tokenizes words, then calls helper to add articles
	public void addArticle(Article article, Map<YearMonth, Map<String, Integer>> map) {
		
		String date = article.getDate().toString();
		String[] titleTokens = Tokenizer.tokenize(article.getTitle());
		String[] bodyTokens = Tokenizer.tokenize(article.getBody());
		
		addArticleHelper(titleTokens, date, map);
		addArticleHelper(bodyTokens, date, map);
		
	}
	
	// logic for adding article to monthWordFrequencyMap
	private static void addArticleHelper(String[] tokens, String rawDate, Map<YearMonth, Map<String, Integer>> map) {
		
		YearMonth date = DateFormatter.formatPeriod(rawDate);
		
		// if map doesnt have given date, make a new entry with date and empty map of word frequencies
		if (!map.containsKey(date)) {
			Map<String, Integer> newFreqMap = new HashMap<>();
			map.put(date, newFreqMap);
		}
		
		Map<String, Integer> freqMap = map.get(date);
		
		for (String token : tokens) {
			
			// skips stopwords
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