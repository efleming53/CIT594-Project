package dto;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateFormatter {
	
	private DateFormatter() {};
	
	public static LocalDate formatLocalDate(String date) {
		try {
			
			return LocalDate.parse(date, DateTimeFormatter.ofPattern("M/d/yyyy"));

		} catch (DateTimeParseException error) {
			// call logger
			return null;
		}
	}
	
    public static YearMonth formatPeriod(String date) {
        try {
            return YearMonth.parse(date, DateTimeFormatter.ofPattern("yyyy-MM"));
        } catch (DateTimeParseException e) {
            // log error
            return null;
        }
    }
    
    public static LocalDate formatDate(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            // log error
            return null;
        }
    }

}
