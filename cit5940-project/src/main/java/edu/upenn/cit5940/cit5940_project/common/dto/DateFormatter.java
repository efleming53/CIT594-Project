package edu.upenn.cit5940.cit5940_project.common.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateFormatter {
	
	private DateFormatter() {};
	
	public static LocalDate formatDate(String date) {
		try {
			
			return LocalDate.parse(date, DateTimeFormatter.ofPattern("M/d/yyyy"));

		} catch (DateTimeParseException error) {
			// call logger
			return null;
		}
	}

}
