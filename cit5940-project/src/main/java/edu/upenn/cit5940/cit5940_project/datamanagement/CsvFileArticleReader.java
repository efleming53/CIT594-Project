package edu.upenn.cit5940.cit5940_project.datamanagement;

import java.util.*;
import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import edu.upenn.cit5940.cit5940_project.common.dto.*;

public class CsvFileArticleReader implements FileArticleReader {

	@Override
	public List<Article> read(String filepath){
		List<Article> articles = new ArrayList<>();
		
		try (CSVReader csvReader = new CSVReader(new FileReader(filepath));) {
			String[] record;
			
			while ((record = csvReader.readNext()) != null) {
				// validate record
				if (record.length != 16 ||
					record[0] == null ||
					record[0].isBlank() ||
					record[1] == null ||
					record[1].isBlank() ||
					record[4] == null ||
					record[4].isBlank() ||
					record[5] == null) {
					// call logger to log error
					continue;
				}
				articles.add(new Article(record));
			}
			
		// switch to calling logger to log warning		
		} catch (IOException | CsvValidationException error) {
			System.out.println("Error opening csv file, " + filepath + error.getMessage());

		}
		return articles;
	}
}
