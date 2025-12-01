package edu.upenn.cit5940.common.dto;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// methods for parsing and formatting dates
public final class DateFormatter {
	
	private DateFormatter() {};
	
	// parses and formats date in pattern M/d/yyyy, returns null if unable to
	public static LocalDate formatLocalDate(String date) {
		
		try {
			
			return LocalDate.parse(date, DateTimeFormatter.ofPattern("M/d/yyyy"));

		} catch (DateTimeParseException error) {
			
			return null;
		}
	}
	
	// parses and formats pariod in format yyyy-MM, returns null if unable to
    public static YearMonth formatPeriod(String date) {
    	
        try {
        	
            return YearMonth.parse(date, DateTimeFormatter.ofPattern("yyyy-MM"));
            
        } catch (DateTimeParseException e) {
            
            return null;
        }
    }
    
    // parses and formats date in format yyyy-MM-dd, returns null if unable to
    public static LocalDate formatDate(String date) {
    	
        try {
        	
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            
        } catch (DateTimeParseException e) {
            
            return null;
        }
    }

}