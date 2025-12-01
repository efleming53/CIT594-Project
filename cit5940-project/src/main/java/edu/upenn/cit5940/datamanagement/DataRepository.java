package edu.upenn.cit5940.datamanagement;

import java.util.*;

import edu.upenn.cit5940.common.dto.*;
import edu.upenn.cit5940.logging.*;
import edu.upenn.cit5940.logging.Logger.LogType;
import java.time.YearMonth;

// holds all datastructures and data for program
public class DataRepository {
	
	private Set<String> articleTitleSet; // set of all titles, used to get number of articles in DataRepository
	private Map<String, Article> articleIdMap; // data for article operation. Key = uri, Value = article
	private Map<String, Set<String>> searchMap; // data for search operation. Key = word, Value = set of titles with word
	private Trie prefixTrie; // data for autocomplete operation, contains all words
	private ArticlesTreeMap articlesTreeMap; // data for articles operation. Key = date, Value = list of titles
	private Map<YearMonth, Map<String, Integer>> monthWordFrequencyMap; // data for topics and trends operations. Key = month, Value = map containing word/frequency pairs
	
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
	
	// loads provided list of articles into each data structure
	public void loadArticles(List<Article> articles) {
		
		SearchMapArticleAdder searchMapAdder = SearchMapArticleAdder.getInstance();
		TrieArticleAdder trieAdder = TrieArticleAdder.getInstance();
		TreeMapArticleAdder treeMapAdder = TreeMapArticleAdder.getInstance();
		MonthWordFrequencyMapArticleAdder monthWordFreqAdder = MonthWordFrequencyMapArticleAdder.getInstance();
		
		// for each article, call respective "addArticle" method for each data structure
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