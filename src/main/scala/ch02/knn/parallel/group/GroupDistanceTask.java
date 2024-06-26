package ch02.knn.parallel.group;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import ch02.knn.data.Distance;
import ch02.knn.data.Sample;
import ch02.knn.distances.EuclideanDistanceCalculator;

/**
 * Task that of the coarse-grained concurrent version 
 * @author author
 *
 */
public class GroupDistanceTask implements Runnable {

	/**
	 * Array of distances
	 */
	private Distance[] distances;
	
	/**
	 * Indexes that determines the examples of the train data this task will process
	 */
	private int startIndex, endIndex;
	
	/**
	 * Example of the test data we want to classify
	 */
	private Sample example;
	
	/**
	 * Data set with the train data examples
	 */
	private List<? extends Sample> dataSet;
	
	/**
	 * Synchronization mechanism to control the end of the task
	 */
	private CountDownLatch endControler;

	/**
	 * Constructor of the class. Initializes all the internal data
	 * @param distances Array of distances
	 * @param startIndex Start index that determines the examples of the train data this task will process
	 * @param endIndex End index that determines the examples of the train data this task will process
	 * @param dataSet Data set with the train data examples
	 * @param example Example of the test data we want to classify
	 * @param endControler Synchronization mechanism to control the end of the task
	 */
	public GroupDistanceTask(Distance[] distances, int startIndex,
			int endIndex, List<? extends Sample> dataSet, Sample example,
			CountDownLatch endControler) {
		this.distances = distances;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.example = example;
		this.dataSet = dataSet;
		this.endControler = endControler;
	}

	@Override
	/**
	 * Concurrent task that calculates the distance between the example and the train instances between
	 * the startIndex and the endIndex 
	 */
	public void run() {
		for (int index = startIndex; index < endIndex; index++) {
			Sample localExample=dataSet.get(index);
			distances[index] = new Distance();
			distances[index].setIndex(index);
				distances[index].setDistance(EuclideanDistanceCalculator
						.calculate(localExample, example));
		}
		endControler.countDown();
	}

}
