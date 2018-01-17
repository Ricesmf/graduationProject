package transition;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 从字符串表示的逻辑表达式转换为逻辑判别结果：true false
 * 
 * @author SMF
 * @time 2018年1月16日
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

		System.out.println("结果类型:" + result.getClass().getName() + ",计算结果:" + result);
		return (boolean) result;
	}
}
