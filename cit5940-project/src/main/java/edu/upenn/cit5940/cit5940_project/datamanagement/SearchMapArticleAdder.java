package edu.upenn.cit5940.cit5940_project.datamanagement;

import java.util.*;
import edu.upenn.cit5940.cit5940_project.common.dto.*;

public class SearchMapArticleAdder implements ArticleAdder<Map<String, Set<String>>> {
	
	private static final SearchMapArticleAdder INSTANCE = new SearchMapArticleAdder();
	
	private SearchMapArticleAdder() {};
	
	public static SearchMapArticleAdder getInstance() {
		return INSTANCE;
	}

	public void addArticle(Article article, Map<String, Set<String>> map) {
		
		String[] titleTokens = Tokenizer.tokenize(article.getTitle());
		String[] bodyTokens = Tokenizer.tokenize(article.getBody());
		
		addArticleHelper(titleTokens, article, map);
		addArticleHelper(bodyTokens, article, map);
		
	}
	
	private static void addArticleHelper(String[] tokens, Article article, Map<String, Set<String>> map) {
		
		for (String token : tokens) {
			
			if (StopWords.WORDS.contains(token)){
				continue;
			}
			
			
			if (map.containsKey(token)) {
				Set<String> keywordArticleSet = map.get(token);
				keywordArticleSet.add(article.getTitle());
			} else {
				Set<String> newSet = new HashSet<>();
				newSet.add(article.getTitle());
				map.put(token, newSet);
			}
			
		}
	}
}
