package transition;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * ���ַ�����ʾ���߼����ʽת��Ϊ�߼��б�����true false
 * 
 * @author SMF
 * @time 2018��1��16��
 */
public class TestStringToBoll2 {

	public static void main(String[] args) {

		String str1 = "x";
		String str2 = ">= 2";
		System.out.println(getBooleanFromString(str1, str2));

	}

	public static boolean getBooleanFromString(String str1, String str2) {
		String str = str1 + str2;
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		Object result = null;
		try {
			engine.put("x", 6);
			result = engine.eval(str);
		} catch (ScriptException e) {
			e.printStackTrace();
		}

		System.out.println("�������:" + result.getClass().getName() + ",������:" + result);
		return (boolean) result;
	}
}
