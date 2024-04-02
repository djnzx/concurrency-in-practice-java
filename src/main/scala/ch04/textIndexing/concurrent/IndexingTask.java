package ch04.textIndexing.concurrent;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Callable;

import ch04.textIndexing.common.Document;
import ch04.textIndexing.common.DocumentParser;

public class IndexingTask implements Callable<Document> {

	private File file;
	
	public IndexingTask(File file) {
		this.file=file;
	}
	@Override
	public Document call() throws Exception {
		DocumentParser parser = new DocumentParser();
	
		Map<String, Integer> voc = parser.parse(file.getAbsolutePath());
		
		Document document=new Document();
		document.setFileName(file.getName());
		document.setVoc(voc);
		return document;
	}

}
