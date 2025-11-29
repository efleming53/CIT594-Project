package processor;

import java.util.*;

import datamanagement.DataRepository;
import dto.FreqPair;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TopicProcessor {
	
	private final DataRepository dr;
	
	public TopicProcessor(DataRepository dr) {
		this.dr = dr;
	}


	// powers topics operation
	public List<FreqPair> getTopTenTopicsOfMonth(YearMonth month){
			
		Map<YearMonth, Map<String, Integer>> map = dr.getMonthWordFrequencyMap();
			
		List<FreqPair> topTen = new ArrayList<>();
		PriorityQueue<FreqPair> freqHeap = new PriorityQueue<>(new FreqPairComparator());
			
		if (!map.containsKey(month)) {
				
		} else {
			Map<String, Integer> wordFreq = map.get(month);
			Set<Map.Entry<String, Integer>> pairs = wordFreq.entrySet();
				
			for(Map.Entry<String, Integer> pair : pairs) {
				FreqPair newPair = new FreqPair(pair.getKey(), pair.getValue());
				freqHeap.add(newPair);
			}
			
			//set loop condition to remove top k items. 10 if # of words > 10, set k to # of words if < 10
			int k = 10;
			if (freqHeap.size() < 10) {
				k = freqHeap.size();
			}
			
			// while i < value we set remove top k items from max heap and add to list we will return
			for (int i = 0; i < k; i++) {
				FreqPair pair = freqHeap.remove();
				topTen.add(pair);
			}			
		}
		return topTen;
	}
	
	// powers trends operation
	public List<Integer> getTopicFrequencyForMonthsInPeriod(String topic, YearMonth start, YearMonth end){
	
		Map<YearMonth, Map<String, Integer>> map = dr.getMonthWordFrequencyMap();
		
		List<Integer> frequencies = new ArrayList<Integer>();
		
		YearMonth currMonth = start;
		
		while (!currMonth.isAfter(end)) {
			
			if (!map.containsKey(currMonth)) {
				frequencies.add(0);
				currMonth = currMonth.plusMonths(1);
				continue;
			}
			
			Map<String, Integer> monthFrequencies = map.get(currMonth);
			
			if (!monthFrequencies.containsKey(topic)) {
				frequencies.add(0);
				currMonth = currMonth.plusMonths(1);
				continue;
			}
			
			Integer frequency = monthFrequencies.get(topic);
			frequencies.add(frequency);
			currMonth = currMonth.plusMonths(1);
		}
		//logger
		return frequencies;
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
