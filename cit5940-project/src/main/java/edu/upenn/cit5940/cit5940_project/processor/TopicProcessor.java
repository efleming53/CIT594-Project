package edu.upenn.cit5940.cit5940_project.processor;

import java.util.*;

import edu.upenn.cit5940.cit5940_project.datamanagement.DataRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TopicProcessor {

	class freqPair{
		
		String word;
		Integer frequency;
		
		freqPair(String word, Integer frequency){
			this.word = word;
			this.frequency = frequency;
		}
	}
		
	public List<freqPair> getTopTenTopicsOfMonth(String month){
			
		DataRepository dr = DataRepository.getDataRepository();
		Map<LocalDate, Map<String, Integer>> map = dr.getMonthWordFrequencyMap();
			
		List<freqPair> topTen = new ArrayList<>();
		PriorityQueue<freqPair> freqHeap = new PriorityQueue<>(new freqPairComparator());
			
		LocalDate date = parseDate(month);
			
		if (!map.containsKey(date)) {
				
		} else {
			Map<String, Integer> wordFreq = map.get(date);
			Set<Map.Entry<String, Integer>> pairs = wordFreq.entrySet();
				
			for(Map.Entry<String, Integer> pair : pairs) {
				freqPair newPair = new freqPair(pair.getKey(), pair.getValue());
				freqHeap.add(newPair);
			}
			
			//TODO: validate size of heap
			for (int i = 0; i < 10; i++) {
				freqPair pair = freqHeap.remove();
				topTen.add(pair);
			}			
		}
		return topTen;
	}
	
	private LocalDate parseDate(String dateStr) {
		
		try {
			//TODO: null check parse
			LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM"));
			return date;
			
		} catch (DateTimeParseException error) {
			// call logger
			return null;
		}
	
	}	
	
    public class freqPairComparator implements Comparator<freqPair> {
    	
        @Override
        public int compare(freqPair fp1, freqPair fp2) {
            return fp2.frequency.compareTo(fp1.frequency);
        }
    }
}
