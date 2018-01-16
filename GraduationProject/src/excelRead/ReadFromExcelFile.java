package excelRead;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ReadFromExcelFile {

	private Logger LOG = Logger.getLogger(ReadFromExcelFile.class);

	private static Workbook wb;

	public ReadFromExcelFile(String filepath) {
		if (null == filepath) {
			return;
		}
		// ��ȡ
		String fileSuffix = filepath.substring(filepath.lastIndexOf('.'));
		try {
			InputStream is = new FileInputStream(filepath);
			if (".xsl".equals(fileSuffix)) {
				wb = new HSSFWorkbook(is);
			} else if (".xlsx".equals(fileSuffix)) {
				wb = new XSSFWorkbook(is);
			} else {
				wb = null;
			}
		} catch (FileNotFoundException e) {
			LOG.error("FileNotFoundException", e);
		} catch (IOException e) {
			LOG.error("IOException", e);
		}
	}

	public Map<Integer, Map<Integer, JSONObject>> getContent() {
		if (null == wb) {
			return null;
		}
		Map<Integer, Map<Integer, JSONObject>> res = new HashMap<Integer, Map<Integer, JSONObject>>();
		int sheetNum = wb.getNumberOfSheets();
		if (sheetNum > 0) {
			for (int i = 0; i < sheetNum; i++) {
				Sheet sheet = wb.getSheetAt(i);
				int rowNum = sheet.getLastRowNum();
				if (rowNum > 0) {
					// ��һ��Ϊ��ͷ�Լ��������˵��
					Row row = sheet.getRow(2);
					int colNum = row.getPhysicalNumberOfCells();
					// ���Ĵӵ����п�ʼ
					for (int j = 2; j < rowNum; j++) {
						row = sheet.getRow(j);
						// map<�У���Ԫ����JSON������>
						Map<Integer, JSONObject> cellContent = new HashMap<Integer, JSONObject>();
						for (int k = 1; k < colNum; k++) {
							// ��Ԫ����JSON������
							JSONObject content = JSON.parseObject(row.getCell(k).toString());
							cellContent.put(k, content);
						}
						res.put(j, cellContent);
					}
				}

			}
		}
		return res;
	}
}
