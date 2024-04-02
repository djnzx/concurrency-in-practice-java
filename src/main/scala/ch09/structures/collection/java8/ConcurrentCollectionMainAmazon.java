package ch09.structures.collection.java8;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentLinkedDeque;

import ch09.structures.hash.data.ConcurrentLoaderAccumulator;
import ch09.structures.hash.data.Product;

public class ConcurrentCollectionMainAmazon {

	public static void main(String[] args) {

		Path file = Paths.get("data\\category");

		ConcurrentLinkedDeque<Product> productList;
		try {
			productList = Files.walk(file, FileVisitOption.FOLLOW_LINKS).parallel()
					.filter(f -> f.toString().endsWith(".txt")).collect(ConcurrentLinkedDeque<Product>::new,
							new ConcurrentLoaderAccumulator(), ConcurrentLinkedDeque::addAll);
			
			
			System.out.println("Products: "+productList.size());
			productList.removeIf(product -> product.getSalesrank() > 1000);
			System.out.println("Products; "+productList.size());
			productList.forEach(product -> {
				System.out.println(product.getTitle()+": "+product.getSalesrank());
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
