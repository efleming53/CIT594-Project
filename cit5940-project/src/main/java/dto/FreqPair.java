package dto;

import java.util.Comparator;

public class FreqPair {
	String word;
	Integer frequency;
	
	public FreqPair(String word, Integer frequency){
		this.word = word;
		this.frequency = frequency;
	}
	
	public String getWord() {
		return word;
	}
	
	public Integer getFrequency() {
		return frequency;
	}
}
