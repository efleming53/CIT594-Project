package datamanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import dto.Article;

import java.time.LocalDate;

public class TreeMapArticleAdder implements ArticleAdder<ArticlesTreeMap> {
	
	private static final TreeMapArticleAdder INSTANCE = new TreeMapArticleAdder();
	
	private TreeMapArticleAdder() {};
	
	public static TreeMapArticleAdder getInstance() {
		return INSTANCE;
	}
	
	public void addArticle(Article article, ArticlesTreeMap treemap) {
		
		LocalDate date = article.getDate();
		TreeMap<LocalDate, List<String>> map = treemap.getArticlesMap();
    	
		// if map contains date, add title to that dates list of dates
    	if (map.containsKey(date)) {
        	List<String> titles = map.get(date);
        	titles.add(article.getTitle());
        // if date not in map, create new list of titles, add title and put date with new list in map
    	} else {
    		List<String> newTitles = new ArrayList<>();
    		newTitles.add(article.getTitle());
    		map.put(date, newTitles);
    	}  
	}

}
