package ch04.bestMatching.serial;

import java.util.ArrayList;
import java.util.List;

import ch04.bestMatching.common.BestMatchingData;
import ch04.bestMatching.distance.LevenshteinDistance;

public class BestMatchingSerialCalculation {
	
	public static BestMatchingData getBestMatchingWords(String word, List<String> dictionary) {
		List<String> results=new ArrayList<String>();
		int minDistance=Integer.MAX_VALUE;
		int distance;
		for (String str: dictionary) {
			distance= LevenshteinDistance.calculate(word,str);
			if (distance<minDistance) {
				results.clear();
				minDistance=distance;
				results.add(str);
			} else if (distance==minDistance) {
				results.add(str);
			}
		}
		
		BestMatchingData result=new BestMatchingData();
		result.setWords(results);
		result.setDistance(minDistance);
		return result;
	}

}
