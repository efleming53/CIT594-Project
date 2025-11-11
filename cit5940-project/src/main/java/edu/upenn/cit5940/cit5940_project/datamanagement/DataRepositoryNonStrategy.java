package edu.upenn.cit5940.cit5940_project.datamanagement;

import java.util.*;
import edu.upenn.cit5940.cit5940_project.common.dto.*;

public class DataRepositoryNonStrategy {
	
	private Map<String, Article> searchMap;
	private Trie prefixTrie;
	private ArticlesTreeMap articlesMap;
	private Map<String, Integer> wordFrequencyMap; // values for monthlyWordMap, ties word to frequency
	private Map<String, Map<String, Integer>> monthlyWordMap;
	
	private DataRepositoryNonStrategy() {
		searchMap = new HashMap<>();
		prefixTrie = new Trie();
		articlesMap = new ArticlesTreeMap();
		monthlyWordMap = new HashMap<>();
	}
	
	private static DataRepositoryNonStrategy dataRepo = new DataRepositoryNonStrategy();
	
	public static DataRepositoryNonStrategy getDataRepositoryNonStrategy(){
		return dataRepo;
	}
	
	
}
