package edu.upenn.cit5940.cit5940_project.common.dto;

public class Tokenizer {
	
	private Tokenizer() {};
	
	private static Tokenizer tokenizer = new Tokenizer();
	
	public static Tokenizer getInstance() {
		return tokenizer;
	}

    /*
    This method returns an array of tokens from a given string
    @param String text
    @return returns an array of tokens
    */
   // tokenize
    public String[] tokenize(String text) {
    		
    	text = text.toLowerCase().replaceAll("[^a-z0-9\\s-]", " ").replaceAll("\\b-+", "").replaceAll("-+\\b",""); // converts text to lowercase, replaces punctuation with spaces
    	
    	String[] tokens = text.split("\\s+"); // split text into tokens along one or more instances of whitespace
    	
    	return tokens;
    }
}
