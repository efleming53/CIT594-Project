package edu.upenn.cit5940.cit5940_project.processor;

import java.util.*;

import edu.upenn.cit5940.cit5940_project.datamanagement.DataRepository;
import edu.upenn.cit5940.cit5940_project.common.dto.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TopicProcessor {


		
	public List<FreqPair> getTopTenTopicsOfMonth(String month){
			
		DataRepository dr = DataRepository.getDataRepository();
		Map<LocalDate, Map<String, Integer>> map = dr.getMonthWordFrequencyMap();
			
		List<FreqPair> topTen = new ArrayList<>();
		PriorityQueue<FreqPair> freqHeap = new PriorityQueue<>(new FreqPairComparator());
			
		LocalDate date = parseDate(month);
			
		if (!map.containsKey(date)) {
				
		} else {
			Map<String, Integer> wordFreq = map.get(date);
			Set<Map.Entry<String, Integer>> pairs = wordFreq.entrySet();
				
			for(Map.Entry<String, Integer> pair : pairs) {
				FreqPair newPair = new FreqPair(pair.getKey(), pair.getValue());
				freqHeap.add(newPair);
			}
			
			//TODO: validate size of heap
			for (int i = 0; i < 10; i++) {
				FreqPair pair = freqHeap.remove();
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
	
    public class FreqPairComparator implements Comparator<FreqPair> {
    	
        @Override
        public int compare(FreqPair fp1, FreqPair fp2) {
            return fp2.getFrequency().compareTo(fp1.getFrequency());
        }
    }

}
