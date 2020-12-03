package cn.leisore.books.java7_in_action.nio;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import cn.leisore.util.Objects;

public class InvokeLogger<T> implements InvocationHandler {

	T obj = null;
	int counter = 1;

	InvokeLogger(T obj) {
		this.obj = obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		if (args != null) {
			for (Object o : args) {
				sb.append(String.valueOf(o)).append(",");
			}
		}
		if (sb.length() > 1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(")");

		Object ret = null;
		Exception ex = null;
		try {
			ret = method.invoke(obj, args);
		}catch (Exception e) {
			ex = e;
		} finally {
			if (ex == null) {
				System.out.println(counter++ + ". " + method + ":  " + sb.toString() + " -> " + String.valueOf(ret));
			} else {
				System.out.println(counter++ + ". " + method + ":  " + sb.toString() + " -> Exception:");
				ex.printStackTrace();
			}
		}

		return ret;
	}
}
