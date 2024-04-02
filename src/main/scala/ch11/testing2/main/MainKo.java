package ch11.testing2.main;

import ch11.testing2.tc.TestClassKo;
import ch11.testing2.common.Data;

import edu.umd.cs.mtc.TestFramework;

public class MainKo {

	public static void main(String[] args) {
		
		Data data=new Data();
		data.setData(10);
		TestClassKo ko=new TestClassKo(data,10);

		try {
			TestFramework.runOnce(ko);
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}
