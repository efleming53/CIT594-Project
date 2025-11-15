package edu.upenn.cit5940.cit5940_project.common.dto;

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
