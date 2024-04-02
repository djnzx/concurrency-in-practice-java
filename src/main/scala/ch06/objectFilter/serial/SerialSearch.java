package ch06.objectFilter.serial;

import java.util.ArrayList;
import java.util.List;

import ch06.objectFilter.common.data.CensusData;
import ch06.objectFilter.common.data.Filter;
import ch06.objectFilter.common.data.FilterData;

public class SerialSearch {
	
	public static CensusData findAny (CensusData[] data, List<FilterData> filters) {

		int index=0;
		for (CensusData censusData : data) {
			if (Filter.filter(censusData, filters)) {
				System.out.println("Found: "+index);
				return censusData;
			}
			index++;
		}
		
		return null;
	}
	
	public static List<CensusData> findAll (CensusData[] data,  List<FilterData> filters) {
		List<CensusData> results=new ArrayList<CensusData>();
		
		for (CensusData censusData : data) {
			if (Filter.filter(censusData, filters)) {
				results.add(censusData);
			}
		}
		
		return results;
	}

}
