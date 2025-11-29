package processor;

import java.util.*;

import datamanagement.ArticlesTreeMap;
import datamanagement.DataRepository;
import dto.Article;

import java.time.LocalDate;

public class ArticleProcessor {
	
	private final DataRepository dr;
	private final ArticlesTreeMap treemap;
	private final Map<String, Article> map;
	private final Set<String> set;
	
	public ArticleProcessor(DataRepository dr) {
		this.dr = dr;
		this.treemap = dr.getArticlesMap();
		this.map = dr.getArticleIdMap();
		this.set = dr.getArticleTitleSet();
	}
	
	public Article getArticleById(String uri) {
		
		if (!map.containsKey(uri)) {
			return null;
		}
		
		return map.get(uri);
	}
	
	public Set<String> getArticleTitlesInPeriod(LocalDate start, LocalDate end) {
		Set<String> titles = new HashSet<>();
		
		LocalDate curr = start;
		
		while (!curr.isAfter(end)) {
			if (treemap.getArticlesMap().containsKey(curr)) {
				List<String> dateTitles = treemap.getArticlesMap().get(curr);
				titles.addAll(dateTitles);
			}
			curr = curr.plusDays(1);
		}
		//logger
		return titles;
	}
	
	public Integer getNumberOfArticles() {
		//logger
		return set.size();
	}

}
