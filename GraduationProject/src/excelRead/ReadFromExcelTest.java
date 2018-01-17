package excelRead;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class ReadFromExcelTest {

	public static void main(String[] args) {
		try {
			String filepath = "F:/learngit/GraduationProject/eventmatrix1.xlsx";
			ReadFromExcelFile excelReader = new ReadFromExcelFile(filepath);
			Map<Integer, Map<Integer, JSONObject>> res = excelReader.getContent();
			System.out.println("获得Excel表格的内容:");
			for (Integer i : res.keySet()) {
				Map<Integer, JSONObject> cellContent = res.get(i);
				for (Integer j : cellContent.keySet()) {
					System.out.println(String.format("第%s行，第%s列内容为%s", i, j, cellContent.get(j)));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}