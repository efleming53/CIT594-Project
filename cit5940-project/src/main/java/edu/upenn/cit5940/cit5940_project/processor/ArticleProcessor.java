package edu.upenn.cit5940.cit5940_project.processor;

import java.util.*;
import java.time.LocalDate;

import edu.upenn.cit5940.cit5940_project.common.dto.*;
import edu.upenn.cit5940.cit5940_project.datamanagement.*;

public class ArticleProcessor {
	
	DataRepository dr = DataRepository.getDataRepository();
	ArticlesTreeMap treemap = dr.getArticlesMap();
	Map<String, Article> map = dr.getArticleIdMap();
	
	//TODO
	public Article getArticleById(String uri) {
		
		if (!map.containsKey(uri)) {
			return null;
		}
		
		return map.get(uri);
	}
	
	public List<String> getArticleTitlesInPeriod(LocalDate start, LocalDate end) {
		List<String> titles = new ArrayList<>();
		
		if (start.isAfter(end)) {
			//logger
		}
		
		
		
		return titles;
	}

}
