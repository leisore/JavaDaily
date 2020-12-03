package cn.leisore.books.java8_in_action.part2.ch4;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import cn.leisore.books.java8_in_action.Dish;

public class Main {

	public static void main(String[]args) {
		List<Dish> menu = Arrays.asList(
				new Dish[] { 
						new Dish("beef", 8000),
						new Dish("tomoto", 500),
						new Dish("fish", 100),
						new Dish("water", 10),
						new Dish("meat", 5000)
						});
		
		menu.stream().forEach(System.out::println);
		menu.stream().forEach(t -> System.out.println(t.getName()));
		menu.stream().filter(((Predicate<Dish>)(t -> t.getCalories() > 100)).and(t -> t.getCalories() < 1000));
		
		Predicate<Dish> p1 = t -> t.getCalories() > 100;
		Predicate<Dish> p2 = t -> t.getCalories() < 1000;
		menu.stream().filter(p1.and(p2));
		
	}
}
