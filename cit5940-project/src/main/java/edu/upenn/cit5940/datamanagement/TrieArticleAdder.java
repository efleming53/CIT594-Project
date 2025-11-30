package edu.upenn.cit5940.datamanagement;

import edu.upenn.cit5940.common.dto.*;


public class TrieArticleAdder implements ArticleAdder<Trie> {
	
	private static final TrieArticleAdder INSTANCE = new TrieArticleAdder();
	
	private TrieArticleAdder() {};
	
	public static TrieArticleAdder getInstance() {
		return INSTANCE;
	}
	
	public void addArticle(Article article, Trie trie) {
		
		String[] titleTokens = Tokenizer.tokenize(article.getTitle());
		String[] bodyTokens = Tokenizer.tokenize(article.getBody());
		
		addArticleHelper(titleTokens, trie);
		addArticleHelper(bodyTokens, trie);
	}
	
	private void addArticleHelper(String[] tokens, Trie trie) {
		
		for (String token : tokens) {
			
			if (StopWords.WORDS.contains(token)) {
				continue;
			}
			trie.insertWord(token);
		}
	}

}