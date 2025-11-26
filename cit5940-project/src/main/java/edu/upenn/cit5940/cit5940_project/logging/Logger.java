package edu.upenn.cit5940.cit5940_project.logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

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
	
	public void log(LogType type, String context) {
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))){
			writer.write(LocalDateTime.now() + " " + type + " " + context);
			writer.newLine();
		} catch (IOException e) {
			//TODO catch error
		}
	}
}
