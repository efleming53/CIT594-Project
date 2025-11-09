package edu.upenn.cit5940.cit5940_project.datamanagement;

import java.util.*;
import edu.upenn.cit5940.cit5940_project.common.dto.*;

public class ArticlesTreeMap {

    private TreeMap<String, List<String>> articlesMap;

    public ArticlesTreeMap() {
    	articlesMap = new TreeMap<>();
    }
    
    public TreeMap<String, List<String>> getArticlesMap() {
    	return articlesMap;
    }

    public void addArticle(String date, String title) {
    	
		// if map contains date, add title to that dates list of dates
    	if (articlesMap.containsKey(date)) {
        	List<String> titles = articlesMap.get(date);
        	titles.add(title);
        // if date not in map, create new list of titles, add title and put date with new list in map
    	} else {
    		List<String> newTitles = new ArrayList<>();
    		newTitles.add(title);
    		articlesMap.put(date, newTitles);
    	}   	
    }
}