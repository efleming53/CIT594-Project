# Project Report

## Part 1: Usage Instructions

### Steps to compile and run the program from terminal

1. Navigate to the directory containing the src folder: cd src
2. Compile all java files under src: javac src/**/*.java
3. Create runnable .jar file: jar --create --file technews.jar --main-class edu.upenn.cit5940.Main -C src .

Along with the file name, the program supports up to two optional command  
line arguments.
 - First optional argument: Date file path - a .csv or .json file containing the Article data.
      If not specified, the default data file path is articles.csv
 - Second optional argument: Log file path - the output file which the program's event logger 
      will write to. If not specified, the default log file path is tech_news_search.log

4. Run the program using, at minimum: java -jar technews.jar (uses default data and log file paths)

Other example launch commands:
	 - java -jar technews.jar path/to/data.json (uses data file path "path/to/data.json"
		and default log file path tech_news_search.log)
	 - java -jar technews.jar path/to/data.json logs/my_run.log (uses data file path
	    "path/to/data.json" and log file path "logs/my_run.log)

### Usage

At program start, the Main Menu displays the following services:
1. Interactive Mode: a guided menu best for new users
2. Command Mode: allows direct commands, best for experienced users
3. Help: displays available services and date formats
4. Exit: ends the program

### Available Services

For each of the following services, interactive mode will prompt the user for each piece of input step-by-step. Usage for command mode is specified:

● search <keyword(s)>: Returns a list of article titles that contain all of the search keywords (an AND operation), case-insensitively. The search is performed on the indexed keywords from the articles; since stop words are not indexed, searching for them will not yield any matches. Each matching title should be printed on a new line. If no articles are found, display a message like "No articles found."
● autocomplete <prefix>: Return a list of potential full words that start with the given prefix. The system should display a maximum of 10 suggestions. If 10 or fewer words match the prefix, display all of them. If more than 10 words match, display only the first 10.
● topics <period>: Analyze word frequencies (ignoring stop words) for a given month (YYYY-MM) and display the top 10 trending words. Your application must validate the period format. If the format or value is invalid (e.g., 2024-13), display an appropriate error message.
● trends <topic> <start> <end>: Shows the monthly frequency of a given topic within an inclusive date range. Both <start> and <end> periods must be in YYYY-MM format, and the start period cannot be after the end. If validation fails, display an error message. The output should list each month in the range with the topic's word count.
● articles <start_date> <end_date>: Return the titles of all articles published within the specified date range, inclusive. The dates must be in YYYY-MM-DD format. Your application must validate that both dates are logically valid (e.g., not 2024-04-31) and that the start date is not after the end date. If any date validation fails, display an appropriate error message. Matching titles should be printed on a new line, sorted chronologically. If no articles are found, display a "No articles found" message.
● article <id>: Retrieve and display the details of a specific article by its unique ID (URI).
● stats: Show data statistics (e.g., total number of articles).
● help: Display a list of available commands  
● menu: Return to the Main Menu.

## Part 2: System Design

### System Architecture

The program implements an n-tier architecture as follows:

- edu.upenn.cit5940.Main is the program's entry point. Parses optional arguments then loads data and runs CLI to run application
- Presentation Tier: handles all user interaction (CLI input/output). Receives commands from user then passes to Application/Log Tier. Once Presentation Tier receives results of operation from Application/Logic Tier, unpackages the results and displays to user Package: edu.upenn.cit5940.ui
- Application/Logic Tier: handles all internal logic. Receives commands from Presentation Tier and retrieves necessary data from Data Tier before executing requested operation, then returns results to Presentation Tier. Package: edu.upenn.cit5940.processor
- Data Tier: Composed of a Data Repository that stores all data in respective data structures so that the Application/Logic Tier can retrieve data to perform the required operation. Main calls the Data Tier's filereaders to read data from data file path and article adders to add the read data into the Data Repository. Package: upenn.cit5940.datamanagement
- Model/DTO Package: Shared object and utility classes used by all tiers including classes such as Article, Tokenizer, DateFormatter, etc. Package: edu.upenn.cit5940.common.dto
- Logger Package: logs program events to log file path. Common logging events include program startup, user input, operation start and completion, errors, and program end. Package: edu.upenn.cit5940.logging

### Data Structures & Refactoring

Inverted Index Refactoring: refactoring the inverted index from a Binary Search Tree to a HashMap greatly improves the performance of the search operation. This is because the time complexity of a lookup in a Binary Search Tree is O[log n] in the average case where the tree is balanced and O[n] in the worst case when the tree is unbalanced (completely skewed). Meanwhile, HashMaps provide lookups in O[1] time so that the efficiency of a lookup for the search operation can be improved from logarithmic/linear time to constant time.

Autocomplete Operation - Trie: A Trie is used for the autocomplete operation because it avoids the need to inspect every word contained in the data, which would result in a time complexity of O[n]. This inefficiency would result if we tried implementing this operation using a List instead of a Trie. WIth the Trie, We may traverse the Trie's nodes along a path that contains the given prefix, and once the prefix is found, all complete words that extend from the prefixe's path can be considered completions of the prefix.

Articles Operation - TreeMap: A TreeMap is used for the Articles operation because a TreeMap automatically maintains order, as the key for each entry in the articles TreeMap is a date that must be kept in chronological order. The TreeMap also provides lookups and insertion in O[log n] time, which is more efficient than using a List, which would provide lookups and insertion in O[n] time

Topics and Trends Operation - Heap: A heap is used to support the Topics and Trends operations not only because it provides O[log n] lookups and insertions, but because the heap automatically maintains order with the maximum value always at the root (the heap is a min-heap by default but the one in this program has been built to work as a max-heap). The heap also allows us to retrieve the max value in O[1] time since the max value is always at the root of the tree. If we used a List to support these operations, there would then be a need to maintain order manually.

Topics and Trends Operation - HashMap: The Topics and Trends operations also use a HashMap to store months along with all of the words and their frequencies that can be found in those months. Again, the HashMap provides us with a very efficient lookup in O[1] time

Article Operation - HashMap: Using a HashMap for the Article operation allows us to lookup the Article with the given uri in O[1] time as well


### Design Patterns

Two design patterns were implemented in this program:

1. Singleton Design Pattern:
The Singleton Design Pattern was used for my Logger and DataRepository classes.

The Logger was implemented as a Singleton instance because there should only be one logger instance that is used by all classes so that logging remains consistent and ensures that the logger always writes to the same file.

As shown in the implementation below, the logger has a private constructor and creates a single instance that is only accessible using the getInstance method. Once another class retrieves the instance, it can then use the Logger's get and set methods to set and access the data and log file paths and can call the log method to write to the log.

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

The DataRepository was also implemented as a Singleton instance so that there can be only one gold standard source of information in the program, preventing any inconsistencies in storing and accessing information across multiple instances of a data store. This ensures that all classes and calls are accessing the same dataset.

In DataRepository's implementation below, much like the Logger, a private constructor initializes all data structures, which are private to the DataRepository class. The getInstance method then allows any of the processor classes to retrieve the data repository and the get methods for each data structure allows a processor class to retrieve the data structure it needs to execute an operation.

// holds all datastructures and data for program
public class DataRepository {
	
	private Set<String> articleTitleSet; // set of all titles, used to get number of articles in DataRepository
	private Map<String, Article> articleIdMap; // data for article operation. Key = uri, Value = article
	private Map<String, Set<String>> searchMap; // data for search operation. Key = word, Value = set of titles with word
	private Trie prefixTrie; // data for autocomplete operation, contains all words
	private ArticlesTreeMap articlesTreeMap; // data for articles operation. Key = date, Value = list of titles
	private Map<YearMonth, Map<String, Integer>> monthWordFrequencyMap; // data for topics and trends operations. Key = month, Value = map containing word/frequency pairs
	
	Logger logger = Logger.getInstance();
	
	private DataRepository() {
		articleTitleSet = new HashSet<>();
		articleIdMap = new HashMap<>();
		searchMap = new HashMap<>();
		prefixTrie = new Trie();
		articlesTreeMap = new ArticlesTreeMap();
		monthWordFrequencyMap = new HashMap<>();
	}
	
	private static DataRepository dataRepo = new DataRepository();
	
	public static DataRepository getInstance(){
		return dataRepo;
	}
	
	// loads provided list of articles into each data structure
	public void loadArticles(List<Article> articles) {
		
		SearchMapArticleAdder searchMapAdder = SearchMapArticleAdder.getInstance();
		TrieArticleAdder trieAdder = TrieArticleAdder.getInstance();
		TreeMapArticleAdder treeMapAdder = TreeMapArticleAdder.getInstance();
		MonthWordFrequencyMapArticleAdder monthWordFreqAdder = MonthWordFrequencyMapArticleAdder.getInstance();
		
		// for each article, call respective "addArticle" method for each data structure
		for (Article article : articles) {
			articleTitleSet.add(article.getTitle());
			articleIdMap.put(article.getUri(), article);
			searchMapAdder.addArticle(article, searchMap);
			trieAdder.addArticle(article, prefixTrie);
			treeMapAdder.addArticle(article, articlesTreeMap);
			monthWordFreqAdder.addArticle(article, monthWordFrequencyMap);
		}	
		
		logger.log(LogType.INFO, articleTitleSet.size() + " articles loaded into DataRepository");
	}
	
	public Set<String> getArticleTitleSet(){
		return articleTitleSet;
	}
	
	public Map<String, Article> getArticleIdMap(){
		return articleIdMap;
	}
	
	public Map<String, Set<String>> getSearchMap(){
		return searchMap;
	}
	
	public Trie getPrefixTrie() {
		return prefixTrie;
	}
	
	public ArticlesTreeMap getArticlesMap() {
		return articlesTreeMap;
	}
	
	public Map<YearMonth, Map<String, Integer>> getMonthWordFrequencyMap() {
		return monthWordFrequencyMap;
	}
}

2. Strategy Design Pattern

The Strategy design pattern is implemented twice in this program to support similar operations that are happening under varying contexts

The FileArticleReader interface declares a type of class that is used to read data from a file so that it can convert the data into a sequence of Articles and then load them into the data repository:

// interface for readers that will read articles from file
public interface FileArticleReader {
	public void read() throws IOException;
}

The CsvFileArticleReader and JsonFileArticleReader classes then implement this interface and define the read method to read data from files of their respective type. The implementation of the CSV reader:

// reads articles from csv file
public class CsvFileArticleReader implements FileArticleReader {
	
	Logger logger = Logger.getInstance();
	Integer recordNum = 1;
	String filepath;
	
	public CsvFileArticleReader(String filepath) {
		this.filepath = filepath;
	}

	@Override
	// reads and loads articles from filepath into DataRepository
	public void read(){
		
		List<Article> articles = new ArrayList<>();
		
		try (CSVReader csvReader = new CSVReader(new FileReader(filepath));) {
			
			String[] record;
			
			while ((record = csvReader.readNext()) != null) {
				// validate record is correct size and contains required fields (uri, date, title, body)
				if (record.length != 16 ||
					record[0] == null ||
					record[0].isBlank() ||
					record[1] == null ||
					record[1].isBlank() ||
					record[4] == null ||
					record[4].isBlank() ||
					record[5] == null) {
					logger.log(LogType.WARNING, "Error parsing record number " + recordNum);
					recordNum++;
					continue;
				}
				articles.add(new Article(record));
				recordNum++;
			}
			
		// log error and return if error opening file		
		} catch (IOException | CsvValidationException error) {
			logger.log(LogType.ERROR, "Error opening CSV file: " + filepath);
			return;
		}
		
		DataRepository dr = DataRepository.getInstance();
		
		//load articles into the DataRepository
		dr.loadArticles(articles);
		
		return;
	}
}

Main then creates an instance and calls the read method for either class dependent on the file extension of the data file path that is provided (or the default path if the user does not provide one).

The Strategy design pattern is also used for adding articles to the data structures.
The ArticleAdder interface declares an addArticle method:

// interface for classes that will add each article to a respective data structure, strategy design pattern
public interface ArticleAdder<T> {
	
	public void addArticle(Article article, T dataStructure);

}

There is then an implementation of ArticleAdder for each data structure in the data repository, as needed, that defines how an article should be added to that structure in a way that will support the intended operations. SearchMapArticleAdder:

// adds articles to searchMap to power search operation
public class SearchMapArticleAdder implements ArticleAdder<Map<String, Set<String>>> {
	
	private static final SearchMapArticleAdder INSTANCE = new SearchMapArticleAdder();
	
	private SearchMapArticleAdder() {};
	
	public static SearchMapArticleAdder getInstance() {
		return INSTANCE;
	}

	// tokenizes words, then calls helper to add articles
	public void addArticle(Article article, Map<String, Set<String>> map) {
		
		String[] titleTokens = Tokenizer.tokenize(article.getTitle());
		String[] bodyTokens = Tokenizer.tokenize(article.getBody());
		
		addArticleHelper(titleTokens, article, map);
		addArticleHelper(bodyTokens, article, map);
		
	}
	
	// logic for adding articles to searchMap
	private static void addArticleHelper(String[] tokens, Article article, Map<String, Set<String>> map) {
		
		for (String token : tokens) {
			
			//if word is a stopword, skip it so it is not in the map
			if (StopWords.WORDS.contains(token)){
				continue;
			}
			
			// if word is already in the map, get its set of titles and add articles title to the set
			if (map.containsKey(token)) {
				Set<String> keywordArticleSet = map.get(token);
				keywordArticleSet.add(article.getTitle());
			
			// if word not in the map, add the word with a new set
			} else {
				Set<String> newSet = new HashSet<>();
				newSet.add(article.getTitle());
				map.put(token, newSet);
			}
			
		}
	}
}

The Strategy design pattern was used for reading files and populating data structures because reading and populating data structures in the context of this program involve a similar type of algorithm across all readers and adders (reading a file and adding an article to a data structure). However, the specific implementation of those algorithms differ based on the file type and the data structure we are adding to. Using the Strategy design pattern supports modularity, flexibility, and maintainability for these important sections of code.
