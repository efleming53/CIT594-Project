package edu.upenn.cit5940.cit5940_project.datamanagement;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upenn.cit5940.cit5940_project.common.dto.Article;

import edu.upenn.cit5940.cit5940_project.common.dto.Article;

public class JsonFileArticleReader implements FileArticleReader {
	
	@Override
	public List<Article> read(String filepath) throws IOException{
		
		List<Article> articles = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			byte[] jsonData = Files.readAllBytes(Paths.get(filepath));	
			List<Article> allArticles = mapper.readValue(jsonData, new TypeReference<List<Article>>() {});
			
			for (Article article : allArticles) {
				if (article.getUri()== null || article.getUri().isBlank() ||
					article.getDate() == null || article.getDate().isBlank() ||
					article.getTitle() == null || article.getTitle().isBlank() ||
					article.getBody() == null) {
						// call logger to log error
						continue;
					}
				articles.add(article);
			}
		} catch (IOException error) {
			//call logger
		}
		return articles;
	}
}
