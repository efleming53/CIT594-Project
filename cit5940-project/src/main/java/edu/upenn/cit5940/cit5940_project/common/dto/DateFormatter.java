package edu.upenn.cit5940.cit5940_project.common.dto;

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
	
    public static YearMonth formatYearMonth(String date) {
        try {
            return YearMonth.parse(date, DateTimeFormatter.ofPattern("M/d/yyyy"));
        } catch (DateTimeParseException e) {
            // log error
            return null;
        }
    }

}
