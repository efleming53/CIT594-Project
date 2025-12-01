/*
 * I attest that the code in this file is entirely my own except for the starter
 * code provided with the assignment and the following exceptions:
 * <Enter all external resources and collaborations here. Note external code may
 * reduce your score but appropriate citation is required to avoid academic
 * integrity violations. Please see the Course Syllabus as well as the
 * university code of academic integrity:
 *  https://catalog.upenn.edu/pennbook/code-of-academic-integrity/ >
 * Signed,
 * Author: Eric Fleming
 * Penn email: eflem53@seas.upenn.edu
 * Date: 2025-11-30
 */

package edu.upenn.cit5940;

import java.io.IOException;
import edu.upenn.cit5940.common.dto.*;
import edu.upenn.cit5940.logging.*;
import edu.upenn.cit5940.logging.Logger.LogType;
import edu.upenn.cit5940.processor.*;
import edu.upenn.cit5940.ui.*;
import edu.upenn.cit5940.datamanagement.*;

// program entry point
public class Main {
	
	static String dataFilePath = "articles.csv";
	static String logFilePath = "tech_news_search.log";
	
	public static void main(String[] args) {
		
		Logger logger = Logger.getInstance();
		DataRepository dr = DataRepository.getInstance();
		
		SearchProcessor sp = new SearchProcessor(dr);
		TopicProcessor tp = new TopicProcessor(dr);
		ArticleProcessor ap = new ArticleProcessor(dr);
		
		// 2 args means optional dataFilePath provided
		if (args.length == 2) {
			
			dataFilePath = args[1];
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
				
			// or try creating new reader with json path provided and read articles to DataRepository 
			} else if (dataFilePath.endsWith(".json")) {
				
				try {
					FileArticleReader jsonReader = new JsonFileArticleReader(dataFilePath);
					jsonReader.read();
	
				} catch (IOException e) {
					
					dataFilePathNotFound(logger, dataFilePath);
					return;
				}
			}
		
		// 3 args means optional data filepath and log filepath provided
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
		
		// run cli to begin user program
		CLI cli = new CLI(sp, tp, ap);
		cli.runCLI();
		logger.log(LogType.INFO, "Program ended by user");
		return;
	}
	
	// logs program start
	private static void logStart(Logger logger, String dataFilePath, String logFilePath) {
		logger.setLogFilePath(logFilePath);
		logger.log(LogType.INFO, "Program Started");
		logger.log(LogType.INFO, "Data filepath parsed: " + dataFilePath);
		logger.log(LogType.INFO, "log filepath parsed: " + logFilePath);
	}
	
	// handles provided data filepath not found and gracefully exits program
	private static void dataFilePathNotFound(Logger logger, String dataFilePath) {
		logger.log(LogType.ERROR, "Error - file with path not found: " + dataFilePath);
		System.out.println("Error - file not found: " + dataFilePath + "\n"
						 + "Thank you for using Tech News Search Engine!\n"
						 + "Goodbye!\n");
	}
	
	// returns dataFilePath
	public static String getDataFilePath() {
		return dataFilePath;
	}

}