package edu.upenn.cit5940.cit5940_project.datamanagement;

import java.util.*;
import java.time.LocalDate;

import edu.upenn.cit5940.cit5940_project.common.dto.*;

public class DataRepository {
	
	private Set<String> articleTitleSet;
	private Map<String, Set<String>> searchMap;
	private Trie prefixTrie;
	private ArticlesTreeMap articlesMap;
	private Map<LocalDate, Map<String, Integer>> monthWordFrequencyMap; 
	
	private DataRepository() {
		articleTitleSet = new HashSet<>();
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
			articleTitleSet.add(article.getTitle());
			searchMapAdder.addArticle(article, searchMap);
			trieAdder.addArticle(article, prefixTrie);
			treeMapAdder.addArticle(article, articlesMap);
			monthWordFreqAdder.addArticle(article, monthWordFrequencyMap);
		}
	}
	
	public Set<String> getArticleTitleSet(){
		return new HashSet<>(articleTitleSet);
	}
	
	public Map<String, Set<String>> getSearchMap(){
		return new HashMap<>(searchMap);
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
