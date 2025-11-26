package edu.upenn.cit5940.cit5940_project.datamanagement;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upenn.cit5940.cit5940_project.common.dto.Article;
import edu.upenn.cit5940.cit5940_project.logging.Logger;
import edu.upenn.cit5940.cit5940_project.logging.Logger.LogType;
import edu.upenn.cit5940.cit5940_project.common.dto.Article;

public class JsonFileArticleReader implements FileArticleReader {
	
	String filepath;
	Integer articleNum = 1;
	Logger logger = Logger.getInstance();
	
	public JsonFileArticleReader(String filepath) {
		this.filepath = filepath;
	}
	
	@Override
	public void read() throws IOException{
		
		List<Article> articles = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			byte[] jsonData = Files.readAllBytes(Paths.get(filepath));	
			List<Article> allArticles = mapper.readValue(jsonData, new TypeReference<List<Article>>() {});
			
			for (Article article : allArticles) {
				//validate article
				if (article.getUri()== null || article.getUri().isBlank() ||
					article.getDate() == null ||
					article.getTitle() == null || article.getTitle().isBlank() ||
					article.getBody() == null) {
						logger.log(LogType.WARNING, "Error parsing record number " + articleNum);
						articleNum++;
						continue;
					}
				articles.add(article);
			}
		} catch (IOException error) {
			logger.log(LogType.ERROR, "Error opening JSON file: " + filepath);
			return;
		}
		
		DataRepository dr = DataRepository.getInstance();
		//load articles into the DataRepository
		dr.loadArticles(articles);
		return;
	}
}
