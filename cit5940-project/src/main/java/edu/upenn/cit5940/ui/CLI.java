package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.logging.*;
import edu.upenn.cit5940.logging.Logger.LogType;
import edu.upenn.cit5940.processor.*;

import java.util.*;

import edu.upenn.cit5940.*;
import edu.upenn.cit5940.common.dto.*;


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
	
	// CLI entry point, displays startup messages then main menu
	public void runCLI() {
		
		//CLI init printouts
		System.out.println("=== Tech News Search Engine ===\n"
						 + "Initializing n-tier architecture...\n"
						 + "Loading articles from filepath: " + Main.getDataFilePath()
						 + ap.getNumberOfArticles() + "aticles loaded\n"
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
			
			// handle scanner does not have more input
			if (!scanner.hasNextLine()) {
				logger.log(LogType.INFO, "Scanner received EOF in Main Menu");
				return;
			}
			
			// receive input
			String rawInput = scanner.nextLine();
			logger.log(LogType.INFO, "User input: " + rawInput);
			String input = rawInput.trim().toLowerCase();
			
			// handle empty input
			if (input.isEmpty()) {
				logger.log(LogType.WARNING, "User input empty in Main Menu");
				System.out.println("Error - empty input. Please enter a choice");
				continue;				
			}
			
			// input must be parsable to int
			try {
				Integer.parseInt(input);
			} catch (NumberFormatException e){
				logger.log(LogType.WARNING, "User entered non-numeric input in Main Menu");
				System.out.println("Error - non-numeric input. Please enter a valid number (1-4)");
				continue;
			}
			
			switch(input) {
				
				//interactive mode selected
				case "1": 
				logger.log(LogType.INFO, "User initiated Interactive Mode");
				runInteractiveMode();
				break;				
				
				// command mode selected
				case "2":
				logger.log(LogType.INFO, "User initiated Command Mode");
				runCommandMode();
				break;				
				
				// help selected
				case "3":
				logger.log(LogType.INFO, "User requested help in Main Menu");
				displayHelp();
				break;
				
				// program exit selected
				case "4":
				logger.log(LogType.INFO, "User requested program exit");
				programExit();
				return;
				
				// handle unknown command
				default: 
				logger.log(LogType.WARNING, "User entered invalid input in main menut");
				System.out.println("Error - Unknown command. Please enter a number option between 1 and 4.\n\n");
				break;
			}
		}
	}
	
	private void runInteractiveMode() {

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
			
			// handle scanner receiving EOF
			if (!scanner.hasNextLine()) {
				logger.log(LogType.INFO, "Scanner received EOF in Interactive Mode");
				return;
			}
			
			// get input
			String input = scanner.nextLine().trim();
			logger.log(LogType.INFO, "User input: " + input);
			
			// handle empty input
			if (input.isEmpty()) {
				System.out.println("Error - Input empty, please enter a choice");
				logger.log(LogType.WARNING, "User input empty in Interactive Mode");
				continue;
			}
			
			// input must be parsable to int
			try {
				Integer.parseInt(input);
			} catch (NumberFormatException e){
				logger.log(LogType.WARNING, "User entered non-numeric input in Interactive Mode");
				System.out.println("Error - non-numeric input. Please enter a valid number (1-8)");
				continue;
			}
			
			switch(input) {
				
				// search operation
				case "1":
					logger.log(LogType.INFO, "User initiated search operation from Interactive Mode");
					String[] keywords = validateKeywords();
					logger.log(LogType.INFO, "Initiating search operation with keywords: " + keywords);
					runSearchCommand(keywords);
					logger.log(LogType.INFO, "Search operation complete");
					break;
				
				// autocomplete operation
				case "2":
					logger.log(LogType.INFO, "User initiated autocomplete operation from Interactive Mode");
					String prefix = validateWord();
					if (prefix == null) {
					    System.out.println("Error: empty input - please enter a choice");
					    logger.log(LogType.WARNING, "Null input entered in Interactive Mode");
					    return;
					}
					logger.log(LogType.INFO, "Initiating autocomplete operation with prefix: " + prefix);
					runAutocompleteCommand(prefix);
					logger.log(LogType.INFO, "Autocomplete operation complete");					
					break;
				
			    // topics operation
				case "3":
					logger.log(LogType.INFO, "User initiated topics operation from Interactive Mode");
					YearMonth period = validatePeriod();
					if (period == null) {
					    System.out.println("Error: empty input - please enter a choice");
					    logger.log(LogType.WARNING, "Null input entered in Interactive Mode");
					    return;
					}
					logger.log(LogType.INFO, "Initiating topics operation with period: " + period);
					runTopicsCommand(period);
					logger.log(LogType.INFO, "Topics operation complete");
					break;
				
				// trends operation
				case "4":
					logger.log(LogType.INFO, "User initiated trends operation from Interactive Mode");
					String topic = validateWord();
					if (topic == null) {
					    System.out.println("Error: empty input - please enter a choice");
					    logger.log(LogType.WARNING, "Null input entered in Interactive Mode");
					    return;
					}
					YearMonth startPeriod = validatePeriod();
					if (startPeriod == null) {
					    System.out.println("Error: empty input - please enter a choice");
					    logger.log(LogType.WARNING, "Null input entered in Interactive Mode");
					    return;
					}
					YearMonth endPeriod = validatePeriod();
					if (endPeriod == null) {
					    System.out.println("Error: empty input - please enter a choice");
					    logger.log(LogType.WARNING, "Null input entered in Interactive Mode");
					    return;
					}
					if (startPeriod.isAfter(endPeriod)) {
						logger.log(LogType.WARNING, "Start period after end period when attempting to run trends operation in Interctive Mode");
						System.out.println("Invalid choice - start period must not come after end period\n");
						continue;
					}
					logger.log(LogType.INFO, "Initiating trends operation with , topic, start period, end period: " + topic + " " + startPeriod + " " + endPeriod);
					runTrendsCommand(topic, startPeriod, endPeriod);
					logger.log(LogType.INFO, "Trends operation complete");
					break;
				
				// articles operation
				case "5":
					logger.log(LogType.INFO, "User initiated articles operation from Interactive Mode");
					LocalDate startDate = validateDate();
					if (startDate == null) {
					    System.out.println("Error: empty input - please enter a choice");
					    logger.log(LogType.WARNING, "Null input entered in Interactive Mode");
					    return;
					}
					LocalDate endDate = validateDate();
					if (endDate == null) {
					    System.out.println("Error: empty input - please enter a choice");
					    logger.log(LogType.WARNING, "Null input entered in Interactive Mode");
					    return;
					}
					if (startDate.isAfter(endDate)) {
						//logger
						System.out.println("Invalid choice - start date must not come after end date\n");
						continue;
					}
					logger.log(LogType.INFO, "Initiating articles operation with, start date, end date: " + startDate + " " + endDate);
					runArticlesCommand(startDate, endDate);
					logger.log(LogType.INFO, "Articles operation complete");
					break;
				
				// article operation
				case "6":
					logger.log(LogType.INFO, "User initiated article operation from Interactive Mode");
					String uri = validateWord();
					if (uri == null) {
					    System.out.println("Error: empty input - please enter a choice");
					    logger.log(LogType.WARNING, "Null input entered in Interactive Mode");
					    return;
					}
					logger.log(LogType.INFO, "Initiating article operation with uri: " + uri);
					runArticleCommand(uri);
					logger.log(LogType.INFO, "Article operation complete");
					break;
				
				// stats operation
				case "7":
					logger.log(LogType.INFO, "User initiated stats operation from Interactive Mode");
					runStatsCommand();
					logger.log(LogType.INFO, "Stats operation complete");
					break;
				
				// exit to main menu
				case "8":
					logger.log(LogType.INFO, "User exiting Interactive Mode, returning to Main Menu");
					return;
				
				// handle invalid choice
				default:
					logger.log(LogType.WARNING, "User entered invalid choice in Interactive Mode");
					System.out.println("Invalid choice, please enter 1-8");
			}	
		}
	}
	
	// validates input for keywords in search operations
	private String[] validateKeywords() {
		
		while (true) {
			System.out.println("Please enter keywords to search for separated by a space:\n");
			
			if (!scanner.hasNextLine()) {
				return null;
			}
			
			// get and tokenize keywords
			String input = scanner.nextLine();
			logger.log(LogType.INFO, "User input: " + input);
			String[] keywords = Tokenizer.tokenize(input);
			
			// handle empty input
			if (keywords.length == 0) {
				logger.log(LogType.WARNING, "User entered empty input for keywords in Search operation");
				System.out.println("Please enter a choice.\n");
				continue;
			}		
			return keywords;	
		}

	}
	
	// validates a word has been entered for commands requiring one
	private String validateWord() {
		
		while (true) {
			System.out.println("Please enter a valid word as input:\n");
			
			if (!scanner.hasNextLine()) {
				return null;
			}
			
			String input = scanner.nextLine().trim();
			logger.log(LogType.INFO, "User input: " + input);
			
			if (input.isEmpty()) {
				logger.log(LogType.WARNING, "User entered empty input where word is required for operation");
				System.out.println("Please enter a choice.\n");
				continue;		
			}
			
			return input;
		}
	}
	
	// validates period for operations requiring one
	private YearMonth validatePeriod() {
		
		while (true) {
			System.out.println("Please enter period in format YYYY-MM:\n");
			
			if (!scanner.hasNextLine()) {
				return null;
			}
			
			String input = scanner.nextLine().trim();
			logger.log(LogType.INFO, "User input: " + input);
			
			// handle empty input
			if (input.isEmpty()) {
				logger.log(LogType.WARNING, "User provided empty input where period required");
				System.out.println("Please enter a choice.\n");
				continue;
			}
			
			YearMonth period = DateFormatter.formatPeriod(input);
			if (period == null) {
				logger.log(LogType.WARNING, "User entered invalid period");
				System.out.println("Invalid period - please enter period in format YYYY-MM\n");
				continue;
			}
			
			return period;
		}
	}
	
	// validate date when required for operation
	private LocalDate validateDate() {
		
		while (true) {
			System.out.println("Please enter date in format YYYY-MM-DD:\n");
			
			if (!scanner.hasNextLine()) {
				return null;
			}
			
			String input = scanner.nextLine().trim();
			logger.log(LogType.INFO, "User input: " + input);
			
			// handle empty input
			if (input.isEmpty()) {
				logger.log(LogType.WARNING, "User entered empty input where date required");
				System.out.println("Please enter a choice.\n");
				continue;
			}
			
			LocalDate date = DateFormatter.formatDate(input);
			if (date == null) {
				logger.log(LogType.WARNING, "User entered invalid date");
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
							 + "Enter commands directly. Type 'help' for available commands.\n"
							 + "Type 'menu' to return to the main menu.");
			
			if (!scanner.hasNextLine()) {
				logger.log(LogType.INFO, "Scanner received EOF in Command Mode");
				return;
			}
			
			String[] input = Tokenizer.tokenize(scanner.nextLine());
			logger.log(LogType.INFO, "User input: " + input);
			
			// handle empty input
			if (input[0].isEmpty()) {
				logger.log(LogType.WARNING, "User provided empty input in Command Mode");
				System.out.println("Invalid input - pleaser enter a command\n");
				continue;
			}
			
			switch(input[0]) {
				
				// search operation
				case "search":
					logger.log(LogType.INFO, "User requested search operation in Command Mode");
					String[] keywords = Arrays.copyOfRange(input, 1, input.length);
					logger.log(LogType.INFO, "Initiating search operation with keywords: " + keywords);
					runSearchCommand(keywords);
					logger.log(LogType.INFO, "Search operation complete");
					break;
				
				// autocomplete operation
				case "autocomplete":
					logger.log(LogType.INFO, "User requested autocomplete operation in Command Mode");
					if (input.length == 1) {
						logger.log(LogType.WARNING, "User did not provide prefix for autocomplete command");
						System.out.println("Please provide a prefix for autocomplete command\n");
						continue;
					}
					logger.log(LogType.INFO, "Initiating autocomplete operation with prefix: " + input[1]);
					runAutocompleteCommand(input[1]);
					logger.log(LogType.INFO, "Autocomplete operation complete");
					break;
				
				// topics operation
				case "topics":
					logger.log(LogType.INFO, "User requested tpoics operation in Command Mode");
					if (input.length == 1) {
						System.out.println("Please provide a period for topics commmand in the format YYYY-MM");
						logger.log(LogType.WARNING, "User did not provide period for topics operation");
						continue;
					}
					YearMonth period = DateFormatter.formatPeriod(input[1]);
					if (period == null) {
						logger.log(LogType.INFO, "User provided invalid peroid for topics operation");
						System.out.println("Please enter a valid period in the format YYYY-MM\n");
						continue;
					}
					logger.log(LogType.INFO, "Initiating topics operation with period: " + period);
					runTopicsCommand(period);
					logger.log(LogType.INFO, "Topics operation complate");
					break;
				
				// trends operation
				case "trends":
					logger.log(LogType.INFO, "User requested trends operation in Command Mode");
					if (input.length != 4) {
						logger.log(LogType.WARNING, "User provided invalid number of arguments for trends operation");
						System.out.println("Invalid number of arguments - Please enter 'trends' <topic> <start (YYYY-MM)> <end (YYYY-MM)>\n");
						continue;
					}
					
					YearMonth startPeriod = DateFormatter.formatPeriod(input[2]);
					if (startPeriod == null) {
						logger.log(LogType.WARNING, "User provided empty input for start period");
						System.out.println("Invalid input - start period must be in format YYYY-MM\n");
						continue;
					}
					
					YearMonth endPeriod = DateFormatter.formatPeriod(input[3]);
					if (endPeriod == null) {
						logger.log(LogType.WARNING, "User provided invalid input for end period");
						System.out.println("Invalid input - start period must be in format YYYY-MM\n");
						continue;
					}
					
					// validate start not after end
					if (startPeriod.isAfter(endPeriod)) {
						logger.log(LogType.WARNING, "User provided start period that is after end period");
						System.out.println("Invalid input - start period cannot be after end period\n");
						continue;
					}
					logger.log(LogType.INFO, "Initiating trends operation with, topic, start period, end period: " + input[1] + " " + startPeriod + " " + endPeriod);
					runTrendsCommand(input[1], startPeriod, endPeriod);	
					logger.log(LogType.INFO, "Trends operation complete");
					break;
					
				// articles operation
				case "articles":
					logger.log(LogType.INFO, "User requested articles operation in Command Mode");
					if (input.length != 3) {
						logger.log(LogType.WARNING, "User provided invalid number of arguments for articles operation");
						System.out.println("Invalid number of arguments - please enter 'articles' <start date (YYYY-MM-DD)> <end date (YYYY-MM-DD)>\n");
						continue;
					}
					
					LocalDate startDate = DateFormatter.formatDate(input[1]);
					if (startDate == null) {
						logger.log(LogType.WARNING, "User provided invalid start date");
						System.out.println("Invalid input - please enter start date in format YYYY-MM-DD");
						continue;
					}
					
					LocalDate endDate = DateFormatter.formatDate(input[2]);
					if (endDate == null) {
						logger.log(LogType.WARNING, "User provided invalid end date");
						System.out.println("Invalid input - please enter end date in format YYYY-MM-DD");
						continue;
					}
					
					// validate start date not after end date
					if (startDate.isAfter(endDate)) {
						logger.log(LogType.WARNING, "User provided start date that is after end date");
						System.out.println("Invalid input - start date must not be after end date");
						continue;
					}
					logger.log(LogType.INFO, "Initiating articles operation with, start date, end date: " + startDate + " " + endDate);
					runArticlesCommand(startDate, endDate);
					logger.log(LogType.INFO, "Articles operation complete");
					break;
				
				// article operation
				case "article":
					logger.log(LogType.INFO, "User requested article operation in Command Mode");
					if (input.length != 2) {
						logger.log(LogType.WARNING, "User provided invalid number of arguments for article operation");
						System.out.println("Invalid number of inputs - please enter 'article' <uri>");
						continue;
					}
					logger.log(LogType.INFO, "Initiating article operation with uri: " + input[1]);
					runArticleCommand(input[1]);
					logger.log(LogType.WARNING, "Article operation complete");
					break;
				
				// stats operation
				case "stats":
					logger.log(LogType.INFO, "User requested stats operation in Command Mode");
					runStatsCommand();
					logger.log(LogType.INFO, "Stats operation complete");
					break;
				
				// diplay help
				case "help":
					logger.log(LogType.INFO, "User help in Command Mode");
					displayHelp();
					logger.log(LogType.INFO, "Help displayed to user");
					break;
				
				// return to main menu
				case "menu":
					logger.log(LogType.INFO, "User exiting Command Mode, returning to Main Menu");
					return;
			
				// hanlde invalid entry
				default:
					logger.log(LogType.WARNING, "User provided invalid entry in Command Mode");
					System.out.println("Invalid entry - Please enter a number choice using 1-9\n");
			}
		}
		
	}
	
	// displays available services to user
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
			
			if (!scanner.hasNextLine()) {
				return;
			}
			
			String input = scanner.nextLine();
			logger.log(LogType.INFO, "User input: " + input);
			
			if (input.trim().isEmpty()) {
				return;
			}
			logger.log(LogType.WARNING, "User did not press Enter to return from Help menu");
			System.out.println("Invalid input - please press Enter to return to main menu\n");
		}
		
	}
	
	// prints exit message
	private void programExit() {
		System.out.println("Thank you for using Tech News Search Engine!\n"
						 + "Goodbye!\n");
	}
	
	// runs search operation
	private void runSearchCommand(String[] keywords) {
		
			List<String> titles = sp.articlesContainingAllKeywords(keywords);
			
			// search did not return results
			if (titles.isEmpty()) {
				logger.log(LogType.INFO, "No articles found in search operation with keyords " + keywords);
				System.out.println("No articles found.\n");
				return;
			}
			
			int i = 1;
			
			// prints search results to user
			while (!titles.isEmpty()) {
				String title = titles.remove(titles.size() - 1);
				System.out.println(i + ". " + title);
				i++;
			}
			logger.log(LogType.INFO, "Search results: " + titles);
			return;
		}
	
	
	// runs autocomplete command
	private void runAutocompleteCommand(String prefix) {
		

		List<String> words = sp.allWordsFromPrefix(prefix);
		
		// handle no words found
		if (words.isEmpty()) {
			logger.log(LogType.INFO, "No words found with prefix: " + prefix);
			System.out.println("No words found from prefix: " + prefix);
		}
		
		// display results to user
		System.out.println("Words matching prefix" + prefix + ":\n");
		for (int wordsIndex = 0; wordsIndex < 10 && wordsIndex < words.size(); wordsIndex++) {
			System.out.println((wordsIndex + 1) + ". " + words.get(wordsIndex));
		}
		
		logger.log(LogType.INFO, "Autocomplete results: " + words);
		
	}
	
	// runs topics command
	private void runTopicsCommand(YearMonth period) {
		
		List<FreqPair> topWords = tp.getTopTenTopicsOfMonth(period);
		
		// handle no results
		if (topWords.isEmpty()) {
			logger.log(LogType.INFO, "No topics found in month: " + period);
			System.out.println("Month not found\n");
			return;
		}
		
		// display results
		for (int i = 0; i < topWords.size(); i++) {
			String word = topWords.get(i).getWord();
			Integer frequency = topWords.get(i).getFrequency();
			System.out.println((i + 1) + ". " + word + " - " + frequency + "\n");
		}
		logger.log(LogType.INFO, "Topics displayed to user");
		return;		
	}
	
	// runs trends command
	private void runTrendsCommand(String topic, YearMonth start, YearMonth end) {
			
		List<Integer> frequencies = tp.getTopicFrequencyForMonthsInPeriod(topic, start, end);
			
		// handle no results
		if (frequencies.isEmpty()) {
			logger.log(LogType.INFO, "Topic, " + topic + "not found in range " + start + " - " + end);
			System.out.println("no frequencies found");
			return;
		}
			
		System.out.println("Frequencies for " + topic + " for " + start + " - " + end + ": \n");
			
		YearMonth month = start;
		
		// display results
		for (int i = 0; i < frequencies.size(); i++, month = month.plusMonths(1)) {
			Integer freq = frequencies.get(i);
			System.out.println(month + ": " + freq + "\n");
		}
		logger.log(LogType.INFO, "Results for topic, " + topic + " in range " + start + " - " + end + ": " + frequencies);
		return;
	}
	
	// runs articles operation
	private void runArticlesCommand(LocalDate start, LocalDate end) {
		
		
		Set<String> titlesSet = ap.getArticleTitlesInDateRange(start, end);
			
		// handle no results
		if (titlesSet.isEmpty()) {
			logger.log(LogType.INFO, "No articles found in range: " + start + " - " + end);
			System.out.println("No titles found between dates " + start + " - " + end + ".\n");
			return;
		}
			
		System.out.println("Titles found between dates " + start + " - " + end + ":\n");
			
		List<String> titles = new ArrayList<>(titlesSet);
		
		// display results
		while (!titles.isEmpty()) {
			String title = titles.remove(titles.size() - 1);
			System.out.println(title + "\n");
		}
		logger.log(LogType.INFO, "Articles found in range" + start + " - " + end);
		return;	
	}
	
	// rusn article command
	private void runArticleCommand(String uri) {
		
		Article article = ap.getArticleById(uri);
		
		// hanlde no results
		if (article == null) {
			logger.log(LogType.INFO, "No article found with uri: " + uri);
			System.out.println("Article not found.\n");
			return;
		}
		
		// display results
		System.out.println("URI: " + article.getUri() + "\n" +
						   "Date: " + article.getDate() + "\n" +
						   "Title: " + article.getTitle() + "\n" +
						   "Body: " + article.getBody() + "\n" );
		
		logger.log(LogType.INFO, "Article found with uri, " + uri + ": " + article.getTitle());
	}
	
	// runs stats command
	private void runStatsCommand() {
		Integer numArticles = ap.getNumberOfArticles();
		System.out.println("Stats:\n"
						 + "\n"
						 + "Number of Articles: " + numArticles + "\n");
		logger.log(LogType.INFO, "Number of articles: " + numArticles);
	}	
}