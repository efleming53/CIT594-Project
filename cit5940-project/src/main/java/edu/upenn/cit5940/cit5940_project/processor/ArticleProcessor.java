package edu.upenn.cit5940.cit5940_project.processor;

import java.util.*;
import java.time.LocalDate;

import edu.upenn.cit5940.cit5940_project.common.dto.*;
import edu.upenn.cit5940.cit5940_project.datamanagement.*;

public class ArticleProcessor {
	
	private final DataRepository dr;
	private final ArticlesTreeMap treemap;
	private final Map<String, Article> map;
	
	public ArticleProcessor(DataRepository dr) {
		this.dr = dr;
		this.treemap = dr.getArticlesMap();
		this.map = dr.getArticleIdMap();
	}
	
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
