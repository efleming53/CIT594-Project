package datamanagement;

import java.util.*;

import dto.Article;
import dto.StopWords;
import dto.Tokenizer;


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
			
			//if word is a Stopword, skip it so it is not in the map
			if (StopWords.WORDS.contains(token)){
				continue;
			}
			
			// if word is already in the map, get its set of titles and add articles title to the set
			if (map.containsKey(token)) {
				Set<String> keywordArticleSet = map.get(token);
				keywordArticleSet.add(article.getTitle());
			
			// if word not in the map, add the word with a new set
			} else {
				Set<String> newSet = new HashSet<>();
				newSet.add(article.getTitle());
				map.put(token, newSet);
			}
			
		}
	}
}
