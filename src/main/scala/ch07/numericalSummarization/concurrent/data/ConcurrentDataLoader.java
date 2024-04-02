package ch07.numericalSummarization.concurrent.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import ch07.numericalSummarization.common.Record;

public class ConcurrentDataLoader {

	public static List<Record> load(Path path) throws IOException {
		System.out.println("Loading data");

		List<String> lines = Files.readAllLines(path);

		List<Record> records = lines
				.parallelStream()
				.skip(1)
				.map(l -> l.split(";"))
				.map(t -> new Record(t))
				.collect(Collectors.toList());

		return records;
	}
}
