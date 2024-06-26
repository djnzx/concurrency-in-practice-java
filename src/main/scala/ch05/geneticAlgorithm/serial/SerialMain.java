package ch05.geneticAlgorithm.serial;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

import ch05.geneticAlgorithm.common.DataLoader;
import ch05.geneticAlgorithm.common.Individual;

public class SerialMain {

	public static void main(String[] args) throws IOException {

		Date start, end;

		int generations = Integer.valueOf(args[0]);
		int individuals = Integer.valueOf(args[1]);

		for (String name : new String[] { "lau15_dist", "kn57_dist" }) {
			int[][] distanceMatrix = DataLoader.load(Paths.get("data", name + ".txt"));

			SerialGeneticAlgorithm serialGeneticAlgorithm = new SerialGeneticAlgorithm(distanceMatrix, generations,
					individuals);
			start = new Date();
			Individual result = serialGeneticAlgorithm.calculate();
			end = new Date();
			System.out.println("=======================================");
			System.out.println("Example:"+name);
			System.out.println("Generations: " + generations);
			System.out.println("Population: " + individuals);
			System.out.println("Execution Time: " + (end.getTime() - start.getTime()));
			System.out.println("Best Individual: " + result);
			System.out.println("Total Distance: " + result.getValue());
			System.out.println("=======================================");
		}

	}

}
