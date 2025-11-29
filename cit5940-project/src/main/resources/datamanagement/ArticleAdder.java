package datamanagement;

import dto.Article;

public interface ArticleAdder<T> {
	
	public void addArticle(Article article, T dataStructure);

}
