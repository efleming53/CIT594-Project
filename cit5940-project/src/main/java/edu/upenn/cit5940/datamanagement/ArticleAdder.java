package edu.upenn.cit5940.datamanagement;

import edu.upenn.cit5940.common.dto.*;

// interface for classes that will add each article to a respective data structure, strategy design pattern
public interface ArticleAdder<T> {
	
	public void addArticle(Article article, T dataStructure);

}