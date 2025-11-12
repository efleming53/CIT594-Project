package edu.upenn.cit5940.cit5940_project.datamanagement;

import java.util.*;
import edu.upenn.cit5940.cit5940_project.common.dto.*;

public class SearchMapArticleAdder implements ArticleAdder<HashMap<String, Set<Article>>> {

	public void addArticle(Article article, HashMap<String, Set<Article>> map) {
		
		Tokenizer tokenizer = Tokenizer.getInstance();
		
		String[] titleTokens = tokenizer.tokenize(article.getTitle());
		String[] bodyTokens = tokenizer.tokenize(article.getBody());
		
		addArticleHelper(titleTokens, article, map);
		addArticleHelper(bodyTokens, article, map);
		
	}
	
	private void addArticleHelper(String[] tokens, Article article, HashMap<String, Set<Article>> map) {
		
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
