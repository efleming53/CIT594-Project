package datamanagement;

import java.util.*;

import java.time.LocalDate;

public class ArticlesTreeMap {

    private TreeMap<LocalDate, List<String>> articlesMap;

    public ArticlesTreeMap() {
    	articlesMap = new TreeMap<>();
    }
    
    public TreeMap<LocalDate, List<String>> getArticlesMap() {
    	return articlesMap;
    }

}