package edu.upenn.cit5940.datamanagement;

import edu.upenn.cit5940.common.dto.*;

public interface ArticleAdder<T> {
	
	public void addArticle(Article article, T dataStructure);

}