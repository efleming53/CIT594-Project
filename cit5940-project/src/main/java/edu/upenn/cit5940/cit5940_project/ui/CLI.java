package edu.upenn.cit5940.cit5940_project.ui;

import edu.upenn.cit5940.cit5940_project.processor.*;
import edu.upenn.cit5940.cit5940_project.common.dto.*;

import java.util.*;

public class CLI {
	

	public static void runCLI() {
		
		Scanner scanner = new Scanner(System.in);
		
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
	
	private static void runInteractiveMode(Scanner scanner) {
		
	}
	
	private static void runCommandMode(Scanner scanner) {
		
		while (true) {
			
			SearchProcessor sp = new SearchProcessor();
			TopicProcessor tp = new TopicProcessor();
			ArticleProcessor ap = new ArticleProcessor();
			
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

					System.out.println("Words to search:\n");
					input = scanner.nextLine();
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
					continue;
					
				case "2":
					
					System.out.println("Word to autocomplete:\n");
					input = scanner.nextLine().trim().toLowerCase();
					List<String> words = sp.allWordsFromPrefix(input);
					
					if (words.isEmpty()) {
						System.out.println("No words found from prefix: " + input);
						continue;
					}
					
					System.out.println("Words matching prefix" + input + ":\n");
					
					for (int wordsIndex = 0; wordsIndex < 10 || wordsIndex < words.size(); wordsIndex++) {
						System.out.println((wordsIndex + 1) + ". " + words.get(wordsIndex));
					}
					//logger
					continue;
					
				case "3":
					System.out.println("Enter month to get word freuencies. Must be in YYYY-MM format.\n"
							+ "\n");
					
					
					continue;
					
				case "4":
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
	
	private static void displayHelp() {
		
	}
}
