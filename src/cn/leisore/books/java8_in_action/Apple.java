package cn.leisore.books.java8_in_action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Apple {

	private String color;
	private int weight;

	public Apple(String color, int weight) {
		this.color = color;
		this.weight = weight;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Apple [color=" + color + ", weight=" + weight + "]";
	}

	@FunctionalInterface
	interface Predicate<T> {
		boolean test(T t);
		
		default Predicate<T> or(Predicate<T> other) {
			return (t) -> test(t) || other.test(t);
		}
	}

	public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> predicate) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (predicate.test(apple)) {
				result.add(apple);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		List<Apple> apples = Arrays.asList(new Apple[] { new Apple("red", 100), new Apple("red", 500),
				new Apple("green", 120), new Apple("green", 5), });

		System.out.println("lamda");
		List<Apple> result = filterApples(apples, (Apple a) -> "red".equals(a.getColor()));
		System.out.println(result);

		result = filterApples(apples, (Apple a) -> {return ("red".equals(a.getColor()) || a.getWeight() > 100);});
		System.out.println(result);
		
		result = filterApples(apples, (a) -> {return ("red".equals(a.getColor()) || a.getWeight() > 100);});
		System.out.println(result);
		
		apples.sort((a, b) -> a.getWeight() - b.getWeight());
		System.out.println(apples);
		
		apples.sort(Comparator.comparingInt((Apple a) -> a.getWeight()));
		System.out.println(apples);
		
		apples.sort(Comparator.comparingInt(Apple::getWeight).reversed());
		System.out.println(apples);
	}
}
