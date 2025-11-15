package edu.upenn.cit5940.cit5940_project.datamanagement;

import java.util.*;
import java.time.LocalDate;

import edu.upenn.cit5940.cit5940_project.common.dto.*;

public class DataRepository {
	
	private Map<String, Set<Article>> searchMap;
	private Trie prefixTrie;
	private ArticlesTreeMap articlesMap;
	private Map<LocalDate, Map<String, Integer>> monthWordFrequencyMap; 
	
	private DataRepository() {
		searchMap = new HashMap<>();
		prefixTrie = new Trie();
		articlesMap = new ArticlesTreeMap();
		monthWordFrequencyMap = new HashMap<>();
		
	}
	
	private static DataRepository dataRepo = new DataRepository();
	
	public static DataRepository getDataRepository(){
		return dataRepo;
	}
	
	public void loadArticles(List<Article> articles) {
		
		SearchMapArticleAdder searchMapAdder = SearchMapArticleAdder.getInstance();
		TrieArticleAdder trieAdder = TrieArticleAdder.getInstance();
		TreeMapArticleAdder treeMapAdder = TreeMapArticleAdder.getInstance();
		MonthWordFrequencyMapArticleAdder monthWordFreqAdder = MonthWordFrequencyMapArticleAdder.getInstance();
		
		for (Article article : articles) {
			searchMapAdder.addArticle(article, searchMap);
			trieAdder.addArticle(article, prefixTrie);
			treeMapAdder.addArticle(article, articlesMap);
			monthWordFreqAdder.addArticle(article, monthWordFrequencyMap);
		}
	}
	
	public Map<String, Set<Article>> getSearchMap(){
		return searchMap;
	}
	
	public Trie getPrefixTrie() {
		return prefixTrie;
	}
	
	public ArticlesTreeMap getArticlesMap() {
		return articlesMap;
	}
	
	public Map<LocalDate, Map<String, Integer>> getMonthWordFrequencyMap() {
		return monthWordFrequencyMap;
	}
}
