package edu.upenn.cit5940.cit5940_project.datamanagement;

import java.util.*;
import edu.upenn.cit5940.cit5940_project.common.dto.*;

public class SearchMapArticleAdder implements ArticleAdder<Map<String, Set<Article>>> {
	
	private static final SearchMapArticleAdder INSTANCE = new SearchMapArticleAdder();
	
	private SearchMapArticleAdder() {};
	
	public static SearchMapArticleAdder getInstance() {
		return INSTANCE;
	}

	public void addArticle(Article article, Map<String, Set<Article>> map) {
		
		String[] titleTokens = Tokenizer.tokenize(article.getTitle());
		String[] bodyTokens = Tokenizer.tokenize(article.getBody());
		
		addArticleHelper(titleTokens, article, map);
		addArticleHelper(bodyTokens, article, map);
		
	}
	
	private static void addArticleHelper(String[] tokens, Article article, Map<String, Set<Article>> map) {
		
		for (String token : tokens) {
			
			if (StopWords.WORDS.contains(token)){
				continue;
			}
			
			if (map.containsKey(token)) {
				Set<Article> keywordArticleSet = map.get(token);
				keywordArticleSet.add(article);
			} else {
				Set<Article> newSet = new HashSet<>();
				newSet.add(article);
				map.put(token, newSet);
			}
			
		}
	}
}
