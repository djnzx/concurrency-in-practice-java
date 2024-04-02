package ch09.synchronization.completable;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import ch09.synchronization.data.Product;
import ch09.synchronization.data.ProductLoader;

public class LoadTask implements Supplier<List<Product>> {

	private Path path;
	
	public LoadTask (Path path) {
		this.path=path;
	}
	@Override
	public List<Product> get() {
		List<Product> productList=null;
		try {
			productList = Files.walk(path, FileVisitOption.FOLLOW_LINKS).parallel()
					.filter(f -> f.toString().endsWith(".txt")).map(ProductLoader::load).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return productList;
	}

}
