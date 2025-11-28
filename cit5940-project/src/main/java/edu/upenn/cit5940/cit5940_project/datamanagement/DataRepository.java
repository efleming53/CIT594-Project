package edu.upenn.cit5940.cit5940_project.datamanagement;

import java.util.*;
import java.time.LocalDate;
import java.time.YearMonth;

import edu.upenn.cit5940.cit5940_project.common.dto.*;
import edu.upenn.cit5940.cit5940_project.logging.Logger;
import edu.upenn.cit5940.cit5940_project.logging.Logger.LogType;

public class DataRepository {
	
	private Set<String> articleTitleSet; 
	private Map<String, Article> articleIdMap;
	private Map<String, Set<String>> searchMap; // powers search operation. key = word, value = set of titles with word
	private Trie prefixTrie; //powers autocomplete operation, contains all words
	private ArticlesTreeMap articlesTreeMap;
	private Map<YearMonth, Map<String, Integer>> monthWordFrequencyMap; 
	
	Logger logger = Logger.getInstance();
	
	private DataRepository() {
		articleTitleSet = new HashSet<>();
		articleIdMap = new HashMap<>();
		searchMap = new HashMap<>();
		prefixTrie = new Trie();
		articlesTreeMap = new ArticlesTreeMap();
		monthWordFrequencyMap = new HashMap<>();
		
	}
	
	private static DataRepository dataRepo = new DataRepository();
	
	public static DataRepository getInstance(){
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
		logger.log(LogType.INFO, articleTitleSet.size() + " articles loaded into DataRepository");
	}
	
	public Set<String> getArticleTitleSet(){
		return articleTitleSet;
	}
	
	public Map<String, Article> getArticleIdMap(){
		return articleIdMap;
	}
	
	public Map<String, Set<String>> getSearchMap(){
		return searchMap;
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
