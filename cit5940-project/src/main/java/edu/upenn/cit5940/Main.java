package cit5940;

import java.io.IOException;

import datamanagement.*;
import logging.*;
import logging.Logger.LogType;
import processor.*;
import ui.*;


public class Main {
	
	static String dataFilePath = "articles.csv";
	static String logFilePath = "tech_news_search.log";
	
	public static void main(String[] args) {
		
		Logger logger = Logger.getInstance();
		DataRepository dr = DataRepository.getInstance();
		
		SearchProcessor sp = new SearchProcessor(dr);
		TopicProcessor tp = new TopicProcessor(dr);
		ArticleProcessor ap = new ArticleProcessor(dr);
		

		
		//2 args means optional dataFilePath provided
		if (args.length == 2) {
			
			dataFilePath = args[1];
			logStart(logger, dataFilePath, logFilePath);
			
			//try creating new reader with csv path provided and read articles to DataRepository
			if (dataFilePath.endsWith(".csv")) {
				try {
					FileArticleReader csvReader = new CsvFileArticleReader(dataFilePath);
					csvReader.read();
					//TODO logger
				} catch (IOException e) {
					dataFilePathNotFound(logger, dataFilePath);
					return;
				}
				
			// or try creating new reader with json path provided and read articles to DataRepository 
			} else if (dataFilePath.endsWith(".json")) {
				try {
					FileArticleReader jsonReader = new JsonFileArticleReader(dataFilePath);
					jsonReader.read();
					//TODO logger
				} catch (IOException e) {
					dataFilePathNotFound(logger, dataFilePath);
					return;
				}
			}
		
		//arg length == 3 means optional data filepath and log filepath provided
		} else if (args.length >= 3) {
			dataFilePath = args[1];
			logFilePath = args[2];
			logStart(logger, dataFilePath, logFilePath);
			
			//try creating new reader with csv path provided and read articles to DataRepository
			if (dataFilePath.endsWith(".csv")) {
				try {
					FileArticleReader csvReader = new CsvFileArticleReader(dataFilePath);
					csvReader.read();
				} catch (IOException e) {
					dataFilePathNotFound(logger, dataFilePath);
					return;
				}
			
			//or try creating new reader with json path provided and read articles to DataRepository
			} else if (dataFilePath.endsWith(".json")) {
				
				try {
					FileArticleReader jsonReader = new JsonFileArticleReader(dataFilePath);
					jsonReader.read();
				} catch (IOException e) {
					dataFilePathNotFound(logger, dataFilePath);
					return;
				}
			}
		}
		//run cli to begin user program
		CLI cli = new CLI(sp, tp, ap);
		cli.runCLI();
		logger.log(LogType.INFO, "Program ended by user");
		return;
	}
	
	private static void logStart(Logger logger, String dataFilePath, String logFilePath) {
		logger.log(LogType.INFO, "Program Started");
		logger.log(LogType.INFO, "Data filepath parsed: " + dataFilePath);
		logger.log(LogType.INFO, "log filepath parsed: " + logFilePath);
	}
	
	private static void dataFilePathNotFound(Logger logger, String dataFilePath) {
		logger.log(LogType.ERROR, "Error - file with path not found: " + dataFilePath);
		System.out.println("Error - file not found: " + dataFilePath + "\n"
						 + "Thank you for using Tech News Search Engine!\n"
						 + "Goodbye!\n");
	}
	
	public static String getDataFilePath() {
		return dataFilePath;
	}

}
