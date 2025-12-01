package edu.upenn.cit5940.common.dto;

import java.util.Comparator;

// FreqPair associates a word with its frequency to support topics operation
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