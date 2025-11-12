package edu.upenn.cit5940.cit5940_project.datamanagement;

import java.util.*;
import edu.upenn.cit5940.cit5940_project.common.dto.*;

public class DataRepository {
	
	private Map<String, Set<Article>> searchMap;
	private Trie prefixTrie;
	private ArticlesTreeMap articlesMap;
	private Map<String, Integer> wordFrequencyMap; // values for monthlyWordMap, ties word to frequency
	private Map<String, Map<String, Integer>> monthlyWordMap;
	
	private DataRepository() {
		searchMap = new HashMap<>();
		prefixTrie = new Trie();
		articlesMap = new ArticlesTreeMap();
		monthlyWordMap = new HashMap<>();
	}
	
	private static DataRepository dataRepo = new DataRepository();
	
	public static DataRepository getDataRepository(){
		return dataRepo;
	}
	
}
