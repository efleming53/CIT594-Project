package edu.upenn.cit5940.datamanagement;

import java.util.*;
import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import edu.upenn.cit5940.common.dto.*;
import edu.upenn.cit5940.logging.*;
import edu.upenn.cit5940.logging.Logger.LogType;

// reads articles from csv file
public class CsvFileArticleReader implements FileArticleReader {
	
	Logger logger = Logger.getInstance();
	Integer recordNum = 1;
	String filepath;
	
	public CsvFileArticleReader(String filepath) {
		this.filepath = filepath;
	}

	@Override
	// reads and loads articles from filepath into DataRepository
	public void read(){
		
		List<Article> articles = new ArrayList<>();
		
		try (CSVReader csvReader = new CSVReader(new FileReader(filepath));) {
			
			String[] record;
			
			while ((record = csvReader.readNext()) != null) {
				// validate record is correct size and contains required fields (uri, date, title, body)
				if (record.length != 16 ||
					record[0] == null ||
					record[0].isBlank() ||
					record[1] == null ||
					record[1].isBlank() ||
					record[4] == null ||
					record[4].isBlank() ||
					record[5] == null) {
					logger.log(LogType.WARNING, "Error parsing record number " + recordNum);
					recordNum++;
					continue;
				}
				articles.add(new Article(record));
				recordNum++;
			}
			
		// log error and return if error opening file		
		} catch (IOException | CsvValidationException error) {
			logger.log(LogType.ERROR, "Error opening CSV file: " + filepath);
			return;
		}
		
		DataRepository dr = DataRepository.getInstance();
		
		//load articles into the DataRepository
		dr.loadArticles(articles);
		
		return;
	}
}