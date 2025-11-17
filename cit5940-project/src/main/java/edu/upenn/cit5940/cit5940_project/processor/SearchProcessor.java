package edu.upenn.cit5940.cit5940_project.processor;

import java.util.*;
import java.time.LocalTime;

import edu.upenn.cit5940.cit5940_project.common.dto.*;
import edu.upenn.cit5940.cit5940_project.datamanagement.*;

public class SearchProcessor {
	
	DataRepository dr = DataRepository.getDataRepository();
	
	public List<String> articlesContainingAllKeywords(String[] words){
		

		Map<String, Set<String>> map = dr.getSearchMap();
		Set<String> titles = dr.getArticleTitleSet();
		
		
		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			
			if (StopWords.WORDS.contains(word)) {
				continue;
			}
			
			if (!map.containsKey(word)) {
				return new ArrayList<>();
			}
			
			Set<String> wordTitles = map.get(word);
			titles.retainAll(wordTitles);
		}
		
		List<String> titlesList = new ArrayList<>(titles);
		return titlesList;
	}
	
	//TODO: Make more efficient, trie traversal
	public List<String> allWordsFromPrefix(String prefix){
		
		Trie trie = dr.getPrefixTrie();
		List<String> allWords = trie.allWords();
		List<String> prefixWords = new ArrayList<>();
		
		for (int i = 0; i < allWords.size(); i++) {
			String word = allWords.get(i);
			if (word.startsWith(prefix)) {
				prefixWords.add(word);
			}
		}
		
		return prefixWords;
	}

}
