package edu.upenn.cit5940.cit5940_project.datamanagement;

import edu.upenn.cit5940.cit5940_project.common.dto.*;

public interface ArticleAdder<T> {
	
	public void addArticle(Article article, T dataStructure);

}
