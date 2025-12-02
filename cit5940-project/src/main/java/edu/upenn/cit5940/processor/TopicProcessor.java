package edu.upenn.cit5940.processor;

import java.util.*;

import edu.upenn.cit5940.datamanagement.*;
import edu.upenn.cit5940.common.dto.*;
import java.time.YearMonth;

public class TopicProcessor {
	
	private final DataRepository dr;
	
	public TopicProcessor(DataRepository dr) {
		this.dr = dr;
	}

	// powers topics operation, returns top ten topics of given month
	public List<FreqPair> getTopTenTopicsOfMonth(YearMonth month){
			
		Map<YearMonth, Map<String, Integer>> map = dr.getMonthWordFrequencyMap();
			
		List<FreqPair> topTen = new ArrayList<>();
		PriorityQueue<FreqPair> freqHeap = new PriorityQueue<>(new FreqPairComparator());
		
		// if month is not in the map, an empty list gets returned since there are no topics
		if (!map.containsKey(month)) {
			
			return new ArrayList<>();	
			
		} else {
			
			
			Map<String, Integer> wordFreq = map.get(month); // get map of words and frequencies for given month
			Set<Map.Entry<String, Integer>> pairs = wordFreq.entrySet();
			
			// iterate through all the entries, create word/freq paris and add them all to the max-heap
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
	
	// powers trends operation, returns frequencies of given topic over the given months
	public List<Integer> getTopicFrequencyForMonthsInPeriod(String topic, YearMonth start, YearMonth end){
	
		Map<YearMonth, Map<String, Integer>> map = dr.getMonthWordFrequencyMap();
		
		List<Integer> frequencies = new ArrayList<Integer>();
		
		YearMonth currMonth = start;
		
		while (!currMonth.isAfter(end)) {
			
			// if the current month is not in the map, the topic appears 0 times
			if (!map.containsKey(currMonth)) {
				frequencies.add(0);
				currMonth = currMonth.plusMonths(1);
				continue;
			}
			
			// get word frequncies for current month
			Map<String, Integer> monthFrequencies = map.get(currMonth);
			
			// if topic is not found in that month, add 0 to frequencies as it appears 0 times in that month
			if (!monthFrequencies.containsKey(topic)) {
				frequencies.add(0);
				currMonth = currMonth.plusMonths(1);
				continue;
			}
			
			// if topic is found in month, its frequency gets added to the list of frequencies
			Integer frequency = monthFrequencies.get(topic);
			frequencies.add(frequency);
			currMonth = currMonth.plusMonths(1);
		}

		return frequencies;
	}	
	
	// defines comparator for word/frequency pairs to make heap a max-heap
    public class FreqPairComparator implements Comparator<FreqPair> {
    	
        @Override
        public int compare(FreqPair fp1, FreqPair fp2) {
            return fp2.getFrequency().compareTo(fp1.getFrequency());
        }
    }

}