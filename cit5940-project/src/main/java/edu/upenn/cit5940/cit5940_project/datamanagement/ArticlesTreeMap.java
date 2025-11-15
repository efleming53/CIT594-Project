package edu.upenn.cit5940.cit5940_project.datamanagement;

import java.util.*;
import java.time.LocalDate;
import edu.upenn.cit5940.cit5940_project.common.dto.*;

public class ArticlesTreeMap {

    private TreeMap<LocalDate, List<String>> articlesMap;

    public ArticlesTreeMap() {
    	articlesMap = new TreeMap<>();
    }
    
    public TreeMap<LocalDate, List<String>> getArticlesMap() {
    	return articlesMap;
    }

}