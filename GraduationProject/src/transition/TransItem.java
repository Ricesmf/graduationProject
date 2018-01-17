package transition;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import excelRead.ReadFromExcelFile;

/**
 * https://github.com/Ricesmf/graduationProject
 * 
 */
public class TransItem {

	private String relationStr; // 前序关系等
	private String duration; // 表示转换完成的时间区间；例如[1,5] (1,5]等
	public boolean guard; // 以relationStr和guard共同决定转换是否可以发生，路径可行与否
	public int times;
	public double probability;
	public Set<String> itemToBeCleared;// 集合中元素是时钟元素；表示需要清零重新计时的时钟元素

	public String getRelationStr() {
		return relationStr;
	}

	public void setRelationStr(String relationStr) {
		this.relationStr = relationStr;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public boolean isGuard() {
		return guard;
	}

	public void setGuard(boolean guard) {
		this.guard = guard;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public Set<String> getItemToBeCleared() {
		return itemToBeCleared;
	}

	public void setItemToBeCleared(Set<String> itemToBeCleared) {
		this.itemToBeCleared = itemToBeCleared;
	}

	public TransItem createTransItem(String relationStr, boolean guard, int times,
			double probability, Set<String> itemToBeCleared) {
		TransItem transItem = new TransItem();
		transItem.relationStr = relationStr;
		transItem.guard = guard;
		transItem.times = times;
		transItem.probability = probability;
		transItem.itemToBeCleared = itemToBeCleared;
		return transItem;
	}

	public TransItem[][] BulidMatrixsFromExcel() {
		String filepath = "F:/learngit/GraduationProject/eventmatrix1.xlsx";
		ReadFromExcelFile excelReader = new ReadFromExcelFile(filepath);
		Map<Integer, Map<Integer, JSONObject>> contents = excelReader.getContent();
		if (null != contents && contents.size() > 0) {
			int eventNum = contents.size();
			TransItem[][] res = new TransItem[eventNum][eventNum];
			for (Integer i : contents.keySet()) {
				Map<Integer, JSONObject> cellContent = contents.get(i);
				for (Integer j : cellContent.keySet()) {
					System.out.println(String.format("第%s行，第%s列内容为%s", i, j, cellContent.get(j)));
					String relationStr = cellContent.get(j).getString("relationStr");
					JSONArray guard = cellContent.get(j).getJSONArray("guard");
					int times = cellContent.get(j).getIntValue("times");
					double probability = cellContent.get(j).getDoubleValue("probability");
					JSONArray clearArray = cellContent.get(j).getJSONArray("itemToBeCleared");
					Set<String> itemToBeCleared = new HashSet<String>();
					for (int clearArrayI = 0; clearArrayI < clearArray.size(); clearArrayI++) {
						itemToBeCleared.add(clearArray.getString(clearArrayI));
					}
					res[i - 2][j - 1] = createTransItem(relationStr, getCombinedBoolean(guard), times, probability, itemToBeCleared);
				}
			}
			return res;
		}
		return null;

	}

	public static boolean getCombinedBoolean(JSONArray guard) {
		if (guard != null && guard.size() > 0) {
			for (int i = 0; i < guard.size(); i++) {
				JSONObject singleGuard = guard.getJSONObject(i);
				long time = new Date().getTime();
				if (!getBooleanFromString(singleGuard.getString("variable"), singleGuard.getString("condition"), time)) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean getBooleanFromString(String str1, String str2, long realValue) {
		String str = str1 + str2;
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		Object result = null;
		try {
			engine.put("x", realValue);
			result = engine.eval(str);
		} catch (ScriptException e) {
			e.printStackTrace();
		}

		System.out.println("结果类型:" + result.getClass().getName() + ",计算结果:" + result);
		return (boolean) result;
	}

}