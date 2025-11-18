package edu.upenn.cit5940.cit5940_project;

import java.io.IOException;

import edu.upenn.cit5940.cit5940_project.datamanagement.*;
import edu.upenn.cit5940.cit5940_project.processor.ArticleProcessor;
import edu.upenn.cit5940.cit5940_project.processor.SearchProcessor;
import edu.upenn.cit5940.cit5940_project.processor.TopicProcessor;
import edu.upenn.cit5940.cit5940_project.ui.*;

public class Main {
	
	public static void main(String[] args) {
		
		FileArticleReader csvReader = new CsvFileArticleReader();
		FileArticleReader jsonReader = new JsonFileArticleReader();
		
		DataRepository dr = DataRepository.getDataRepository();
		
		SearchProcessor sp = new SearchProcessor(dr);
		TopicProcessor tp = new TopicProcessor(dr);
		ArticleProcessor ap = new ArticleProcessor(dr);
		
		String dataFilePath = "articles.csv";
		String logFilePath = "tech_news_search.log";
		
		if (args.length == 2) {
			dataFilePath = args[1];
			
			if (dataFilePath.endsWith(".csv")) {

				try {
					csvReader.read(dataFilePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (dataFilePath.endsWith(".json")) {

				try {
					jsonReader.read(dataFilePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (args.length >= 3) {
			dataFilePath = args[1];
			logFilePath = args[2];
			
			if (dataFilePath.endsWith(".csv")) {

				try {
					csvReader.read(dataFilePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (dataFilePath.endsWith(".json")) {

				try {
					jsonReader.read(dataFilePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		CLI cli = new CLI(sp, tp, ap);
		cli.runCLI();
	}

}
