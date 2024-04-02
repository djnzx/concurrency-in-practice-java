package ch11.testing2.main;

import ch11.testing2.tc.TestClassOk;
import ch11.testing2.common.Data;

import edu.umd.cs.mtc.TestFramework;

public class MainOk {

	public static void main(String[] args) {
		
		Data data=new Data();
		data.setData(10);
		TestClassOk ok=new TestClassOk(data,10);

		try {
			TestFramework.runOnce(ok);
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}
