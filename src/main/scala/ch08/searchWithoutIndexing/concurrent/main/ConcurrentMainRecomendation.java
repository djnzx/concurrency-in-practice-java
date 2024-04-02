package ch08.searchWithoutIndexing.concurrent.main;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ch08.searchWithoutIndexing.data.Product;
import ch08.searchWithoutIndexing.data.ProductRecommendation;
import ch08.searchWithoutIndexing.data.ProductReview;

public class ConcurrentMainRecomendation {

	public static void main(String[] args) {
		String user = args[0];
		Path file = Paths.get("data");
		try {
			Date start, end;
			
			start=new Date();
			List<Product> productList = Files
					.walk(file, FileVisitOption.FOLLOW_LINKS)
					.parallel()
					.filter(f -> f.toString().endsWith(".txt"))
					.collect(ArrayList<Product>::new,
							new ConcurrentLoaderAccumulator(),
							List::addAll);
			
			Map<String, List<ProductReview>> productsByBuyer=productList
					.parallelStream()
					.<ProductReview>flatMap(p -> p.getReviews().stream().map(r -> new ProductReview(p, r.getUser(), r.getValue())))
					.collect(Collectors.groupingByConcurrent( p -> p.getBuyer()));
			
			Map<String,List<ProductReview>> recommendedProducts=productsByBuyer.get(user)
					.parallelStream()
					.map(p -> p.getReviews())
					.flatMap(Collection::stream)
					.parallel()
					.map(r -> r.getUser())
					.distinct()
					.map(productsByBuyer::get)
					.flatMap(Collection::stream)
					.parallel()
					.collect(Collectors.groupingByConcurrent(p -> p.getTitle()));
			

			List<ProductRecommendation> recommendations = recommendedProducts
					.entrySet()
					.parallelStream()
					.map(entry -> new ProductRecommendation(
							entry.getKey(), 
							entry.getValue().stream().mapToInt(p -> p.getValue()).average().getAsDouble()))
					.sorted()
					.collect(Collectors.toList());
			
			end=new Date();
			
			recommendations. forEach(pr -> System.out.println(pr.getTitle()+": "+pr.getValue()));

			
			System.out.println("Execution Time: "+(end.getTime()-start.getTime()));

		} catch (IOException e) {
			e.printStackTrace();
		}			
	}

}
