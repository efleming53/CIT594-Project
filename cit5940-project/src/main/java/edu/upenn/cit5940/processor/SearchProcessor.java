package edu.upenn.cit5940.processor;

import java.util.*;
import edu.upenn.cit5940.common.dto.*;
import edu.upenn.cit5940.datamanagement.*;

// methods to execute search and autocomplete operations
public class SearchProcessor {
	
	private final DataRepository dr;
	
	public SearchProcessor(DataRepository dr) {
		this.dr = dr;
	}
	
	// returns titles of articles containing ALL provided words
	public List<String> articlesContainingAllKeywords(String[] words){
		
		Map<String, Set<String>> map = dr.getSearchMap();
		Set<String> titles = dr.getArticleTitleSet();

		for (int i = 0; i < words.length; i++) {
			
			String word = words[i];
			
			// skip stopwords
			if (StopWords.WORDS.contains(word)) {
				continue;
			}
			
			// return empty list if word not in map, because then no articles contain ALL words 
			if (!map.containsKey(word)) {
				return new ArrayList<>();
			}
			
			Set<String> wordTitles = map.get(word);
			titles.retainAll(wordTitles); // titles only keeps titles that the current word is in
		}
		
		List<String> titlesList = new ArrayList<>(titles);
		
		return titlesList;
	}
	
	// returns all words that begin with the given prefix
	public List<String> allWordsFromPrefix(String prefix){
		
		Trie trie = dr.getPrefixTrie();
		List<String> allWords = trie.allWords();
		List<String> prefixWords = new ArrayList<>();
		
		// words in the trie that start with the given prefix get added to prefixWords, then returned
		for (int i = 0; i < allWords.size(); i++) {
			
			String word = allWords.get(i);
			if (word.startsWith(prefix)) {
				prefixWords.add(word);
			}
		}
		
		return prefixWords;
	}
	
	public Integer getNumberOfArticles() {
		Set<String> titles = dr.getArticleTitleSet();	
		return titles.size();
	}

}