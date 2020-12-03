package cn.leisore.util;

public class Objects {

	public static String toFQNString(Object o) {
		if (o == null) {
			return "null";
		}

		if (o instanceof String) {
			return "\"" + o +"\"";
		}

		return "(" + o.getClass().getName() + ")" + String.valueOf(o);
	}
}
