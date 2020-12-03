package cn.leisore._20180326;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class Main {

	public static void main(String[] args) {

		Field[] declaredFields = A.class.getDeclaredFields();
		for (Field f : declaredFields) {
			System.out.println(f.getGenericType());
			System.out.println(f.getName() + "--" + f.getType().isAssignableFrom(Map.class));
		}
		
		Map<String, String> map = new HashMap<String,String>();
		map.put("1", "A");
		String s = JSON.toJSONString(map, false);
		System.out.println(s);
		Map t = JSON.parseObject(s, Map.class);
		System.out.println(t.getClass());
		System.out.println(t);
	}

}

class A {
	Map<String,String> map;
}
