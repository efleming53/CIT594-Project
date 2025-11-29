package dto;

public final class Tokenizer {
	
	private Tokenizer() {};
	
    /*
    This method returns an array of tokens from a given string
    @param String text
    @return returns an array of tokens
    */
   // tokenize
    public static String[] tokenize(String text) {
    		
    	text = text.toLowerCase().replaceAll("[^a-z0-9\\s-]", " ").replaceAll("\\b-+", "").replaceAll("-+\\b",""); // converts text to lowercase, replaces punctuation with spaces
    	
    	String[] tokens = text.split("\\s+"); // split text into tokens along one or more instances of whitespace
    	
    	return tokens;
    }
}
