package cn.leisore._20170810;

import java.lang.instrument.Instrumentation;

public class Agent {
	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println(Agent.class.getClassLoader());
		System.out.println("AAAAAAAAAAAAAAA");
		Holder.inst = inst;
		new Runner().start();
	}
	
	public static void main(String[] args) {
		System.out.println( null instanceof String);
	}
}
