package cn.leisore._20171219;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.ParserConfig;


public class TestFastJson {
	
	
	private final static class Foo {
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public Object getObj() {
			return obj;
		}
		public void setObj(Object obj) {
			this.obj = obj;
		}
		String className;
		Object obj;
		
		@Override
		public String toString() {
			return "Foo [className=" + className + ", obj=" + obj + "]";
		}

	}

	public static void main(String[] args) {

		ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
		Foo foo = new Foo();
		foo.className = Date.class.getName();
		foo.obj = new Date();
		String s = null;
		System.out.println(s=JSON.toJSONString(foo));
		System.out.println(JSON.parseObject(s, Foo.class));
		
		List<Foo> foos = new ArrayList<>();
		foos.add(foo);
		String jsonString = JSON.toJSONString(foos, true);
		System.out.println("===" + jsonString);
		
		JSONArray parseObject = JSON.parseArray(jsonString);
		System.out.println("---" + parseObject);
		
	}
}
