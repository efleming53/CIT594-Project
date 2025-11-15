package edu.upenn.cit5940.cit5940_project.processor;

import java.util.*;
import java.time.LocalTime;

import edu.upenn.cit5940.cit5940_project.common.dto.*;
import edu.upenn.cit5940.cit5940_project.datamanagement.*;

public class SearchProcessor {
	

	
	public Set<String> articlesContainingAllKeywords(List<String> words){
		
		DataRepository dr = DataRepository.getDataRepository();
		Map<String, Set<String>> map = dr.getSearchMap();
		
		Set<String> articleTitles = new HashSet<>();
		
		for (int i = 0; i < words.size(); i++) {
			String word = words.get(i);
			
			if (StopWords.WORDS.contains(word)) {
				continue;
			}
			
			
		}
		
		
		return new HashSet<>();
		
	}

}
