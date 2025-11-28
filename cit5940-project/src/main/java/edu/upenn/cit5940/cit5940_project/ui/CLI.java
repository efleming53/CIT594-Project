package edu.upenn.cit5940.cit5940_project.ui;

import edu.upenn.cit5940.cit5940_project.Main;
import edu.upenn.cit5940.cit5940_project.common.dto.*;
import edu.upenn.cit5940.cit5940_project.logging.Logger;
import edu.upenn.cit5940.cit5940_project.logging.Logger.LogType;
import edu.upenn.cit5940.cit5940_project.processor.*;

import java.util.*;
import java.time.YearMonth;
import java.time.LocalDate;

public class CLI {
	
	private final Scanner scanner;
	private final SearchProcessor sp;
	private final TopicProcessor tp;
	private final ArticleProcessor ap;
	private final Logger logger = Logger.getInstance();
	
	public CLI(SearchProcessor sp, TopicProcessor tp, ArticleProcessor ap) {
		this.scanner = new Scanner(System.in);
		this.sp = sp;
		this.tp = tp;
		this.ap = ap;
	}
	

	public void runCLI() {
		
		//CLI init printouts
		System.out.println("=== Tech News Search Engine ===\n"
						 + "Initializing n-tier architecture...\n"
						 + "Loading articles from filepath: " + Main.getDataFilePath()
						 + sp.getNumberOfArticles() + "aticles loaded\n"
						 + "Architecture initialization complete.\n\n"); 
		logger.log(LogType.INFO, "CLI Running");
		
		while (true) {
			
			// main menu loops until valid input
			System.out.println(
					  "==================================================\n"
					+ "                    MAIN MENU                     \n"
					+ "==================================================\n"
					+ "\n"
					+ "1. Interactive Mode (Guided Menu)\n"
					+ "2. Command Mode\n"
					+ "3. Help\n"
					+ "4. Exit\n"
					+ "==================================================\n"
					+ "Select option using 1-4\n");
			
			String rawInput = scanner.nextLine();
			String input = rawInput.trim().toLowerCase();
			
			if (input.isEmpty()) {
				logger.log(LogType.WARNING, "User input empty in Main Menu");
				System.out.println("Error - empty input. Please enter a choice");
				continue;				
			}
			
			try {
				Integer.parseInt(input);
			} catch (NumberFormatException e){
				logger.log(LogType.WARNING, "User entered non-numeric input in Main Menu");
				System.out.println("Error - non-numeric input. Please enter a valid number (1-4)");
				continue;
			}
			
			switch(input) {
				case "1": 
				runInteractiveMode();
				break;				
			
				case "2":
				runCommandMode();
				break;				
			
				case "3":
				displayHelp();
				break;
					
				case "4":
				programExit();
				return;
				
			
				default: 
				logger.log(LogType.WARNING, "User entered invalid input in main menut");
				System.out.println("Error - Unknown command. Please enter a number option between 1 and 4.\n\n");
				break;
			}
		}
	}
	
	private void runInteractiveMode() {
		logger.log(LogType.INFO, "Interactive mode started");
		while (true) {
			System.out.println("==================================================\n"
					         + "                INTERACTIVE MODE                  \n"
					         + "==================================================\n"
					         + "\n"
					         + " This mode will guide you through each operation step by step.\n"
					         + "----------------------------------------\n"
					         + "                AVAILABLE SERVICES      \n"
					         + "----------------------------------------\n "
					         + "1. Search Articles\n "
					         + "2. Get Autocomplete Suggestions\n "
					         + "3. View Top Topics\n "
					         + "4. Analyze Topic Trends\n "
					         + "5. Browse Articles by Date\n "
					         + "6. View Specific Article by ID\n "
					         + "7. Show Statistics\n "
					         + "8. Back to Main Menu\n "
					         + "----------------------------------------\n "
					         + "Select a service (1-8):\n");
			
			String input = scanner.nextLine().trim();
			
			if (input.isEmpty()) {
				System.out.println("Error - Input empty, please enter a choice");
				logger.log(LogType.WARNING, "User input empty in interactive mode");
				continue;
			}
			
			
			try {
				Integer.parseInt(input);
			} catch (NumberFormatException e){
				logger.log(LogType.WARNING, "User entered non-numeric input in Interactive Mode");
				System.out.println("Error - non-numeric input. Please enter a valid number (1-8)");
				continue;
			}
			
			switch(input) {
			
				case "1":
					String[] keywords = validateKeywords();
					runSearchCommand(keywords);
					break;
					
				case "2":
					String prefix = validateWord();
					runAutocompleteCommand(prefix);
					break;
					
				case "3":
					YearMonth period = validatePeriod();
					runTopicsCommand(period);
					break;
				
				case "4":
					String topic = validateWord();
					YearMonth startPeriod = validatePeriod();
					YearMonth endPeriod = validatePeriod();
					if (startPeriod.isAfter(endPeriod)) {
						logger.log(LogType.WARNING, "Start period after end period when attempting to run trends operation in Interctive Mode");
						System.out.println("Invalid choice - start period must not come after end period\n");
						continue;
					}
					runTrendsCommand(topic, startPeriod, endPeriod);
					break;
				
				case "5":
					//logger
					LocalDate startDate = validateDate();
					LocalDate endDate = validateDate();
					if (startDate.isAfter(endDate)) {
						//logger
						System.out.println("Invalid choice - start date must not come after end date\n");
						continue;
					}
					runArticlesCommand(startDate, endDate);
					break;
				
				case "6":
					//logger
					String uri = validateWord();
					runArticleCommand(uri);
					break;
					
				case "7":
					//logger
					runStatsCommand();
					break;
					
				case "8":
					//logger
					return;
					
				default:
					//logger
					System.out.println("Invalid choice, please enter 1-8");
			}
				
			
		}
	}
	
	private String[] validateKeywords() {
		
		while (true) {
			System.out.println("Please enter keywords to search for separated by a space:\n");
			String input = scanner.nextLine();
			String[] keywords = Tokenizer.tokenize(input);
			
			if (keywords.length == 0) {
				//logger
				System.out.println("Please enter a choice.\n");
				continue;
			}		
			return keywords;	
		}

	}
	
	private String validateWord() {
		
		while (true) {
			System.out.println("Please enter a valid word as input:\n");
			String input = scanner.nextLine().trim();
			
			if (input.isEmpty()) {
				//logger
				System.out.println("Please enter a choice.\n");
				continue;		
			}
			
			return input;
		}
	}
	private YearMonth validatePeriod() {
		
		while (true) {
			System.out.println("Please enter period in format YYYY-MM:\n");
			String input = scanner.nextLine().trim();
			
			if (input.isEmpty()) {
				//logger
				System.out.println("Please enter a choice.\n");
				continue;
			}
			
			YearMonth period = DateFormatter.formatPeriod(input);
			if (period == null) {
				//logger
				System.out.println("Invalid period - please enter period in format YYYY-MM\n");
				continue;
			}
			
			return period;
		}
	}
	
	private LocalDate validateDate() {
		
		while (true) {
			System.out.println("Please enter date in format YYYY-MM-DD:\n");
			String input = scanner.nextLine().trim();
			
			if (input.isEmpty()) {
				//logger
				System.out.println("Please enter a choice.\n");
				continue;
			}
			
			LocalDate date = DateFormatter.formatDate(input);
			if (date == null) {
				//logger
				System.out.println("Invalid date - please enter date in format YYYY-MM-DD\n");
				continue;
			}
			
			return date;
		}
	}
	
	private void runCommandMode() {
		
		while (true) {
			

			
			System.out.println("===============================\n"
							 + "        COMMAND MODE           \n"
							 + "===============================\n"
							 + "\n"
							 + "Please select a command using 1-9\n"
							 + "\n"
							 + "1. Search\n"
							 + "2. Autocomplete\n"
							 + "3. Topics\n"
							 + "4. Trends\n"
							 + "5. Articles\n"
							 + "6. Article\n"
							 + "7. Stats\n"
							 + "8. Help\n"
							 + "9. Menu\n"
							 + "\n");
			
			String[] input = Tokenizer.tokenize(scanner.nextLine());
			
			if (input[0].isEmpty()) {
				System.out.println("Invalid input - pleaser enter a command\n");
				//logger
				continue;
			}
			
			switch(input[0]) {
			
				case "search":
					//logger
					String[] keywords = Arrays.copyOfRange(input, 1, input.length);
					runSearchCommand(keywords);
					break;
					
				case "autocomplete":
					//logger
					if (input.length == 1) {
						System.out.println("Please provide a prefix for autocomplete command\n");
						//logger
						continue;
					}
					//TODO if input.length > 2?
					runAutocompleteCommand(input[1]);
					break;
					
				case "topics":
					//logger
					if (input.length == 1) {
						System.out.println("Please provide a period for topics commmand in the format YYYY-MM");
						//logger
						continue;
					}
					YearMonth period = DateFormatter.formatPeriod(input[1]);
					if (period == null) {
						System.out.println("Please enter a valid period in the format YYYY-MM\n");
						//logger
						continue;
					}
					runTopicsCommand(period);
					break;
					
				case "trends":
					//logger
					if (input.length != 4) {
						System.out.println("Invalid number of arguments - Please enter 'trends' <topic> <start (YYYY-MM)> <end (YYYY-MM)>\n");
						//logger
						continue;
					}
					
					YearMonth startPeriod = DateFormatter.formatPeriod(input[2]);
					if (startPeriod == null) {
						System.out.println("Invalid input - start period must be in format YYYY-MM\n");
						//logger
						continue;
					}
					
					YearMonth endPeriod = DateFormatter.formatPeriod(input[3]);
					if (endPeriod == null) {
						System.out.println("Invalid input - start period must be in format YYYY-MM\n");
						//logger
						continue;
					}
					
					if (startPeriod.isAfter(endPeriod)) {
						System.out.println("Invalid input - start period cannot be after end period\n");
						//logger
						continue;
					}
					
					runTrendsCommand(input[1], startPeriod, endPeriod);	
					break;
					
				case "articles":
					//logger
					if (input.length != 3) {
						System.out.println("Invalid number of arguments - please enter 'articles' <start date (YYYY-MM-DD)> <end date (YYYY-MM-DD)>\n");
						//logger
						continue;
					}
					
					LocalDate startDate = DateFormatter.formatDate(input[1]);
					if (startDate == null) {
						System.out.println("Invalid input - please enter start date in format YYYY-MM-DD");
						//logger
						continue;
					}
					
					LocalDate endDate = DateFormatter.formatDate(input[2]);
					if (endDate == null) {
						System.out.println("Invalid input - please enter end date in format YYYY-MM-DD");
						//logger
						continue;
					}
					
					if (startDate.isAfter(endDate)) {
						System.out.println("Invalid input - start date must not be after end date");
						//logger
						continue;
					}
					
					runArticlesCommand(startDate, endDate);
					break;
					
				case "article":
					//logger
					if (input.length != 2) {
						System.out.println("Invalid number of inputs - please enter 'article' <uri>");
						//logger
						continue;
					}
					
					runArticleCommand(input[1]);
					break;
					
				case "stats":
					//logger
					runStatsCommand();
					break;
					
				case "help":
					//logger
					displayHelp();
					break;
					
				case "menu":
					return;
				
				default:
					System.out.println("Invalid entry - Please enter a number choice using 1-9\n");
			}
		}
		
	}
	
	private void displayHelp() {
		
		while (true) {
			System.out.println("============================================================\n"
							 + "                 HELP & DOCUMENTATION                       \n"
							 + "============================================================\n"
							 + "INTERACTIVE MODE:\n"
							 + "     • Guided step-by-step interface\n "
							 + "     • Prompts for all required inputs\n"
							 + "     • Perfect for beginners\n"
							 + "COMMAND MODE:\n"
							 + "     • Direct command entry\n"
							 + "     • Faster for experienced users\n "
							 + "     • Type 'help' for command list\n "
							 + "AVAILABLE SERVICES:\n "
							 + "     1. Search Articles - Find articles by keywords\n "
							 + "     2. Autocomplete - Get search suggestions\n"
							 + "     3. Top Topics - View trending topics by period\n"
							 + "     4. Topic Trends - Analyze topic popularity over time\n"
							 + "     5. Browse Articles - Filter articles by date range\n"
							 + "     6. View Article - Get detailed article information\n"
							 + "     7. Statistics - View database statistics\n "
							 + "DATE FORMATS:\n"
							 + "     • Period: YYYY-MM (e.g., 2023-12)\n"
							 + "     • Date: YYYY-MM-DD (e.g., 2023-12-01)\n"
							 + "Press Enter to return to main menu...");
			
			String input = scanner.nextLine();
			//TODO: require enter to return to main menu
			if (input.trim().isEmpty()) {
				return;
			}
			//logger
			System.out.println("Invalid input - please press Enter to return to main menu\n");
		}
		
	}
	
	private void programExit() {
		System.out.println("Thank you for using Tech News Search Engine!\n"
						 + "Goodbye!\n");
	}
	
	
	private void runSearchCommand(String[] keywords) {
		
			List<String> titles = sp.articlesContainingAllKeywords(keywords);
			
			if (titles.isEmpty()) {
				System.out.println("No articles found.\n");
				return;
			}
			
			int i = 1;
			
			while (!titles.isEmpty()) {
				String title = titles.remove(titles.size() - 1);
				System.out.println(i + ". " + title);
				i++;
			}
			return;
		}
	
	
	private void runAutocompleteCommand(String prefix) {
		

		List<String> words = sp.allWordsFromPrefix(prefix);
		
		if (words.isEmpty()) {
			System.out.println("No words found from prefix: " + prefix);
		}
		
		System.out.println("Words matching prefix" + prefix + ":\n");
		
		for (int wordsIndex = 0; wordsIndex < 10 && wordsIndex < words.size(); wordsIndex++) {
			System.out.println((wordsIndex + 1) + ". " + words.get(wordsIndex));
		}
		
	}
	
	private void runTopicsCommand(YearMonth period) {
		
		List<FreqPair> topWords = tp.getTopTenTopicsOfMonth(period);
			
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
		return;		
	}
	
	private void runTrendsCommand(String topic, YearMonth start, YearMonth end) {
			
		List<Integer> frequencies = tp.getTopicFrequencyForMonthsInPeriod(topic, start, end);
			
			
		if (frequencies.isEmpty()) {
			System.out.println("no frequencies found");
			return;
		}
			
		System.out.println("Frequencies for " + topic + " for " + start + " - " + end + ": \n");
			
		YearMonth month = start;
			
		for (int i = 0; i < frequencies.size(); i++, month = month.plusMonths(1)) {
			Integer freq = frequencies.get(i);
			System.out.println(month + ": " + freq + "\n");
		}
		return;
	}
	
	private void runArticlesCommand(LocalDate start, LocalDate end) {
		
		
		Set<String> titlesSet = ap.getArticleTitlesInPeriod(start, end);
			
		if (titlesSet.isEmpty()) {
			System.out.println("No titles found between dates " + start + " - " + end + ".\n");
			return;
		}
			
		System.out.println("Titles found between dates " + start + " - " + end + ":\n");
			
		List<String> titles = new ArrayList<>(titlesSet);
			
		while (!titles.isEmpty()) {
			String title = titles.remove(titles.size() - 1);
			System.out.println(title + "\n");
		}
		return;	
	}
	
	private void runArticleCommand(String uri) {
		
		Article article = ap.getArticleById(uri);
		
		if (article == null) {
			System.out.println("Article not found.\n");
		}
		
		System.out.println("URI: " + article.getUri() + "\n" +
						   "Date: " + article.getDate() + "\n" +
						   "Title: " + article.getTitle() + "\n" +
						   "Body: " + article.getBody() + "\n" );
	}
	
	private void runStatsCommand() {
		Integer numArticles = ap.getNumberOfArticles();
		System.out.println("Stats:\n"
						 + "\n"
						 + "Number of Articles: " + numArticles + "\n");
	}	
}

