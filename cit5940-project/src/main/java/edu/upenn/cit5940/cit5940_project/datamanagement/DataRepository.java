package edu.upenn.cit5940.cit5940_project.datamanagement;

import java.util.*;
import java.time.LocalDate;
import java.time.YearMonth;

import edu.upenn.cit5940.cit5940_project.common.dto.*;

public class DataRepository {
	
	private Set<String> articleTitleSet;
	private Map<String, Article> articleIdMap;
	private Map<String, Set<String>> searchMap;
	private Trie prefixTrie;
	private ArticlesTreeMap articlesTreeMap;
	private Map<YearMonth, Map<String, Integer>> monthWordFrequencyMap; 
	
	private DataRepository() {
		articleTitleSet = new HashSet<>();
		articleIdMap = new HashMap<>();
		searchMap = new HashMap<>();
		prefixTrie = new Trie();
		articlesTreeMap = new ArticlesTreeMap();
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
			articleTitleSet.add(article.getTitle());
			articleIdMap.put(article.getUri(), article);
			searchMapAdder.addArticle(article, searchMap);
			trieAdder.addArticle(article, prefixTrie);
			treeMapAdder.addArticle(article, articlesTreeMap);
			monthWordFreqAdder.addArticle(article, monthWordFrequencyMap);
		}
	}
	
	public Set<String> getArticleTitleSet(){
		return new HashSet<>(articleTitleSet);
	}
	
	public Map<String, Article> getArticleIdMap(){
		return new HashMap<>(articleIdMap);
	}
	
	public Map<String, Set<String>> getSearchMap(){
		return new HashMap<>(searchMap);
	}
	
	public Trie getPrefixTrie() {
		return prefixTrie;
	}
	
	public ArticlesTreeMap getArticlesMap() {
		return articlesTreeMap;
	}
	
	public Map<YearMonth, Map<String, Integer>> getMonthWordFrequencyMap() {
		return monthWordFrequencyMap;
	}
}
