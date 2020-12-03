package cn.leisore._20190522;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.esotericsoftware.reflectasm.MethodAccess;

public class ReflectTest {

	public static void main(String[] args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		int count = 1000 * 10000;
		Foo foo = new Foo();

		System.out.println();

		long start = System.currentTimeMillis();
		nativeCall(foo, count);
		System.out.println(String.format("nativeCall %d cost %dms", count, (System.currentTimeMillis() - start)));

		start = System.currentTimeMillis();
		javaReflectCall(foo, count);
		System.out.println(String.format("javaReflectCall %d cost %dms", count, (System.currentTimeMillis() - start)));

		start = System.currentTimeMillis();
		reflectAsmCall(foo, count);
		System.out.println(String.format("reflectAsmCall %d cost %dms", count, (System.currentTimeMillis() - start)));

	}

	static void nativeCall(Foo foo, int count) {
		int n = 0;
		while (n++ < count) {
			foo.bar();
		}
	}

	static Method bar = null;
	static {
		try {
			long start = System.nanoTime();
			bar = Foo.class.getDeclaredMethod("bar");
			System.out.println(String.format("Class.getDeclaredMethod cost %dnanos", (System.nanoTime() - start)));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	static void javaReflectCall(Foo foo, int count)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int n = 0;
		while (n++ < count) {
			bar.invoke(foo);
		}
	}

	static MethodAccess methodAccess;
	static int index;
	static {
		long start = System.nanoTime();

		methodAccess = MethodAccess.get(Foo.class);
		index = methodAccess.getIndex("bar");

		System.out.println(String.format("MethodAccess.get cost %dnanos", (System.nanoTime() - start)));
	}

	static void reflectAsmCall(Foo foo, int count) {
		int n = 0;
		while (n++ < count) {
			methodAccess.invoke(foo, index);
		}
	}
}
