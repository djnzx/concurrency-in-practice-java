package ch04.bestMatching.serial;

import java.util.Date;
import java.util.List;

import ch04.bestMatching.data.WordsLoader;
import ch04.bestMatching.common.BestMatchingData;


public class BestMatchingSerialMain {

	public static void main(String[] args) {

		Date startTime, endTime;
		List<String> dictionary= WordsLoader.load("data/UK Advanced Cryptics Dictionary.txt");
		
		System.out.println("Dictionary Size: "+dictionary.size());
		
		startTime=new Date();
		BestMatchingData result=BestMatchingSerialCalculation.getBestMatchingWords(args[0], dictionary);
		List<String> results=result.getWords();
		endTime=new Date();
		System.out.println("Word: "+args[0]);
		System.out.println("Minimun distance: "+result.getDistance());
		System.out.println("List of best matching words: "+results.size());
		results.forEach(System.out::println);
		System.out.println("Execution Time: "+(endTime.getTime()-startTime.getTime()));
	}

}
