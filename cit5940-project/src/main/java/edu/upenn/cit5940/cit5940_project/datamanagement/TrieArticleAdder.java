package edu.upenn.cit5940.cit5940_project.datamanagement;

import edu.upenn.cit5940.cit5940_project.common.dto.*;

public class TrieArticleAdder implements ArticleAdder<Trie> {
	
	public void addArticle(Article article, Trie trie) {
		
		Tokenizer tokenizer = Tokenizer.getInstance();
		
		String[] titleTokens = tokenizer.tokenize(article.getTitle());
		String[] bodyTokens = tokenizer.tokenize(article.getBody());
		
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
