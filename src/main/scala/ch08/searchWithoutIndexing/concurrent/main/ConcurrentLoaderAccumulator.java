package ch08.searchWithoutIndexing.concurrent.main;

import java.nio.file.Path;
import java.util.List;
import java.util.function.BiConsumer;

import ch08.searchWithoutIndexing.data.Product;
import ch08.searchWithoutIndexing.data.ProductLoader;

public class ConcurrentLoaderAccumulator implements
		BiConsumer<List<Product>, Path> {

	@Override
	public void accept(List<Product> list, Path path) {

		Product product=ProductLoader.load(path);
		list.add(product);
		
	}

}
