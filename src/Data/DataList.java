package Data;

import java.util.ArrayList;

public class DataList {
	private static ArrayList<String> list = new ArrayList<String>();

	public static ArrayList<String> getList() {
		return list;
	}

	public static void setList(ArrayList<String> list) {
		DataList.list = list;
	}
	

}
