package edu.upenn.cit5940.processor;

import java.util.*;
import edu.upenn.cit5940.datamanagement.*;
import edu.upenn.cit5940.common.dto.*;
import java.time.LocalDate;

// methods for executing article and articles operations
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
	
	// executes article operation, returns article with the given id
	public Article getArticleById(String uri) {
		
		// validate article is in map
		if (!map.containsKey(uri)) {
			return null;
		}
		
		// return article
		return map.get(uri);
	}
	
	// executes articles operation, returns titles within start and end dates, inclusive
	public Set<String> getArticleTitlesInDateRange(LocalDate start, LocalDate end) {
		
		Set<String> titles = new HashSet<>();
		
		LocalDate curr = start;
		
		while (!curr.isAfter(end)) {
			
			// if date is in map, get all titles and add to set
			if (treemap.getArticlesMap().containsKey(curr)) {
				List<String> dateTitles = treemap.getArticlesMap().get(curr);
				titles.addAll(dateTitles);
				
			}
			
			curr = curr.plusDays(1); // increments date
		}

		return titles;
	}
	
	/*returns number of articles in DataRepository, 
	 * supports stats operation and providing user 
	 * with number of articles loaded on program startup*/
	public Integer getNumberOfArticles() {
		return set.size();
	}
}