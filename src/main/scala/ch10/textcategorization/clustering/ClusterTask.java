package ch10.textcategorization.clustering;

import ch10.textcategorization.common.Document;
import ch10.textcategorization.common.Vocabulary;

public class ClusterTask implements Runnable {

	private Document documents[];
	private Vocabulary voc;
	
	
	public ClusterTask(Document documents[], Vocabulary voc) {
		this.documents=documents;
		this.voc=voc;
	}
	
	@Override
	public void run() {
		System.out.println("Documents to cluster: "+documents.length);
		ConcurrentKMeans.calculate(documents, 10, voc.getVocabulary().size(), 991, 10);
	}

}
