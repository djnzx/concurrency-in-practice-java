package ch04.bestMatching.serial;

import java.util.List;

import ch04.bestMatching.distance.LevenshteinDistance;

public class ExistSerialCalculation {

	public static boolean existWord(String word, List<String> dictionary) {
		for (String str: dictionary) {
			if (LevenshteinDistance.calculate(word, str)==0) {
				return true;
			}
		}
		return false;
	}
}
