package edu.upenn.cit5940.cit5940_project.ui;

import edu.upenn.cit5940.cit5940_project.processor.*;
import edu.upenn.cit5940.cit5940_project.common.dto.*;

import java.util.*;
import java.time.YearMonth;

public class CLI {
	
	private final Scanner scanner;
	private final SearchProcessor sp;
	private final TopicProcessor tp;
	private final ArticleProcessor ap;
	
	public CLI(SearchProcessor sp, TopicProcessor tp, ArticleProcessor ap) {
		this.scanner = new Scanner(System.in);
		this.sp = sp;
		this.tp = tp;
		this.ap = ap;
	}
	

	public void runCLI() {
		
		
		while (true) {
			
			System.out.println("Select option using 1-4\n"
					+ "\n"
					+ "1. Interactive Mode\n"
					+ "2. Command Mode\n"
					+ "3. Help"
					+ "4. Exit\n");
			
			String rawInput = scanner.nextLine();
			String input = rawInput.trim().toLowerCase();
			
			if (input == "1") {
				runInteractiveMode(scanner);
				continue;				
			}
			
			if (input == "2") {
				runCommandMode(scanner);
				continue;				
			}
			
			if (input == "3") {
				displayHelp();
				continue;				
			}
					
			if (input == "4") {
				return;
			}
			
		System.out.println("Invalid entry - Please enter a number option between 1 and 4.\n"
				+ "\n");
		}
	}
	
	private void runInteractiveMode(Scanner scanner) {
		
	}
	
	private void runCommandMode(Scanner scanner) {
		
		while (true) {
			

			
			System.out.println("==============================="
							 + "        COMMAND MODE           "
							 + "==============================="
							 + ""
							 + "Please select a command using 1-9"
							 + ""
							 + "1. Search"
							 + "2. Autocomplete"
							 + "3. Topics"
							 + "4. Trends"
							 + "5. Articles"
							 + "6. Article"
							 + "7. Stats"
							 + "8. Help "
							 + "9. Menu"
							 + "");
			
			String input = scanner.nextLine();
			
			switch(input) {
			
				case "1":
					runSearchCommand();
					continue;
					
				case "2":
					runAutocompleteCommand();
					continue;
					
				case "3":
					runTopicsCommand();
					continue;
					
				case "4":
					runTrendsCommand();
					continue;
					
				case "5":
					continue;
					
				case "6":
					System.out.println("Uri of article:\n");
					input = scanner.nextLine().trim().toLowerCase();
					Article article = ap.getArticleById(input);
					
					if (article == null) {
						System.out.println("Article not found.\n");
					}
					
					System.out.println("URI: " + article.getUri() +
									   "Date: " + article.getDate() +
									   "Title: " + article.getTitle() +
									   "Body: " + article.getBody());
					//logger
					
					continue;
					
				case "7":
					continue;
					
				case "8":
					continue;
					
				case "9":
					return;
			}
			
			
			
		System.out.println("Invalid entry - Please enter a number choice using 1-9\n");
		}
		
	}
	
	private void displayHelp() {
		
	}
	
	private void runSearchCommand() {
		
		while (true) {
			System.out.println("Words to search:\n");
			String input = scanner.nextLine();
			String[] keywords = Tokenizer.tokenize(input);
			List<String> titles = sp.articlesContainingAllKeywords(keywords);
			
			if (titles.isEmpty()) {
				System.out.println("No articles found.\n");
				continue;
			}
			
			int i = 1;
			
			while (!titles.isEmpty()) {
				String title = titles.remove(titles.size() - 1);
				System.out.println(i + ". " + title);
				i++;
			}
			
			
			//logger
			return;
		}
	}
	
	private void runAutocompleteCommand() {
		
		System.out.println("Word to autocomplete:\n");
		String input = scanner.nextLine().trim().toLowerCase();
		List<String> words = sp.allWordsFromPrefix(input);
		
		if (words.isEmpty()) {
			System.out.println("No words found from prefix: " + input);
		}
		
		System.out.println("Words matching prefix" + input + ":\n");
		
		for (int wordsIndex = 0; wordsIndex < 10 || wordsIndex < words.size(); wordsIndex++) {
			System.out.println((wordsIndex + 1) + ". " + words.get(wordsIndex));
		}
		
	}
	
	private void runTopicsCommand() {
		
		while (true) {
			
			System.out.println("Enter month to get word frequencies. Must be in YYYY-MM format.\n"
					+ "\n");
			String input = scanner.nextLine().trim().toLowerCase();
			YearMonth month = DateFormatter.formatYearMonth(input);
			
			if (month == null) {
				System.out.println("Invalid date - must me in format YYYY-MM\n");
				continue;
			}
			
			List<FreqPair> topWords = tp.getTopTenTopicsOfMonth(month);
			
			if (topWords.isEmpty()) {
				System.out.println("Month not found\n");
				//logger
				return;
			}
			
			for (int i = 0; i < topWords.size(); i++) {
				String word = topWords.get(i).getWord();
				Integer frequency = topWords.get(i).getFrequency();
				System.out.println((i + 1) + ". " + word + " - " + frequency + "\n");
			}
			//logger
			return;
			
		}
	}
	
	private void runTrendsCommand() {
		
	}
}
