package edu.upenn.cit5940.logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

// records important program events to specified log filepath
public class Logger {
	
	String articleFilePath;
	String logFilePath;
	
	public enum LogType{
		INFO,
		ERROR,
		WARNING
	}
	
	private Logger() {}
	
	private static final Logger logger = new Logger();
	
	public static Logger getInstance() {
		return logger;
	}
	
	public String getArticleFilePath() {
		return articleFilePath;
	}
	
	public String getLogFilePath() {
		return logFilePath;
	}
	
	public void setArticleFilePath(String filepath) {
		articleFilePath = filepath;
	}

	public void setLogFilePath(String filepath) {
		logFilePath = filepath;
	}
	
	// writes current time, log type, and provided message to logFilePath
	public void log(LogType type, String context) {
		
		// validate path
		if (logFilePath == null || logFilePath.isBlank()) {
		    return;
		}
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))){
			
			writer.write(LocalDateTime.now() + " " + type + " " + context);
			writer.newLine();
			
		} catch (IOException e) {
			return; // silently ignore logging error, cannot log an error on logging
		}
	}
}