package edu.upenn.cit5940.datamanagement;

import java.util.*;

import java.time.LocalDate;

// TreeMap that will store data for articles operation
public class ArticlesTreeMap {

    private TreeMap<LocalDate, List<String>> articlesMap;

    public ArticlesTreeMap() {
    	articlesMap = new TreeMap<>();
    }
    
    public TreeMap<LocalDate, List<String>> getArticlesMap() {
    	return articlesMap;
    }

}