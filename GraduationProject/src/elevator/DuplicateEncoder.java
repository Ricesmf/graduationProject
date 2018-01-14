package elevator;

import java.util.HashMap;
import java.util.Map;

public class DuplicateEncoder {
	public static void main(String[] args) {
		String s = "  ()(   ";
		System.out.println(encode(s));
	}

	static String encode(String word) {
		int len = word.length();
		word = word.toLowerCase();
		Map<String, Integer> m = new HashMap<String, Integer>();
		for (int i = 0; i < len; i++) {
			String subStr = word.substring(i, i + 1);
			if (m.containsKey(subStr)) {
				m.put(subStr, m.get(subStr) + 1);
			} else {
				m.put(subStr, 1);
			}
		}
		for (int i = 0; i < len; i++) {
			String subStr = word.substring(i, i + 1);
			if (m.get(subStr) == 1) {
				word = word + "(";
				// word = word.replace(subStr, "(");
			} else {
				word = word + ")";
				// word = word.replace(subStr, ")");
			}
		}
		word = word.substring(len);
		return word;
	}
}
