package ch08.searchWithoutIndexing.concurrent.main;

import java.nio.file.Path;
import java.util.List;
import java.util.function.BiConsumer;

import ch08.searchWithoutIndexing.data.Product;
import ch08.searchWithoutIndexing.data.ProductLoader;

public class ConcurrentObjectAccumulator implements
		BiConsumer<List<Product>, Path> {

	private String word;

	public ConcurrentObjectAccumulator(String word) {
		this.word = word;
	}

	@Override
	public void accept(List<Product> list, Path path) {

		Product product=ProductLoader.load(path);
		
		if (product.getTitle().toLowerCase().contains(word.toLowerCase())) {
			list.add(product);
		}
		
	}

}
