package ch08.searchWithoutIndexing.concurrent.main;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch08.searchWithoutIndexing.data.Product;

public class ConcurrentMainSearch {

	public static void main(String args[]) {
		String query = args[0];
		Path file = Paths.get("data");
		try {
			Date start, end;
			start=new Date();
			List<Product> results = Files
					.walk(file, FileVisitOption.FOLLOW_LINKS)
					.parallel()
					.filter(f -> f.toString()
					.endsWith(".txt"))
					.collect(ArrayList<Product>::new,
							new ConcurrentObjectAccumulator(query),
							List::addAll);
			end=new Date();
			System.out.println("Resultados");
			System.out.println("*************");
			results.forEach(p -> System.out.println(p.getTitle()));
			System.out.println("Execution Time: "+(end.getTime()-start.getTime()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
