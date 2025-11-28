package edu.upenn.cit5940.cit5940_project.datamanagement;

import java.util.*;

import edu.upenn.cit5940.cit5940_project.common.dto.*;

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