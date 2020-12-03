package cn.leisore.books.java8_in_action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;;

public class Dish {

	public static enum Type {
		MEAT, OTHER, FISH,
	}

	public Dish(String name, int calories) {
		this.name = name;
		this.calories = calories;
	}

	public Dish(String name, boolean vegetarian, int calories, Type type) {
		this.name = name;
		this.vegetarian = vegetarian;
		this.calories = calories;
		this.type = type;
	}

	private String name;
	private boolean vegetarian;
	private int calories;
	private Type type;

	public String getName() {
		return name;
	}

	public boolean isVegetarian() {
		return vegetarian;
	}

	public int getCalories() {
		return calories;
	}

	public Type getType() {
		return type;
	}

	public static void main(String[] args) {
		List<Dish> menu = Arrays.asList(new Dish[] { new Dish("beef", 8000), new Dish("tomoto", 500),
				new Dish("fish", 100), new Dish("water", 10), new Dish("meat", 5000), });

		// old
		{
			List<Dish> lowCaloricDishes = new ArrayList<>();
			for (Dish d : menu) {
				if (d.getCalories() < 400) {
					lowCaloricDishes.add(d);
				}
			}

			Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
				@Override
				public int compare(Dish o1, Dish o2) {
					return Integer.compare(o1.getCalories(), o2.getCalories());
				}
			});

			List<String> lowCaloricDishesNames = new ArrayList<>();
			for (Dish d : lowCaloricDishes) {
				lowCaloricDishesNames.add(d.getName());
			}

			for (String s : lowCaloricDishesNames) {
				System.out.println(s);
			}
		}

		// stream
		{
			List<String> result = menu.stream()
									.filter(d -> d.getCalories() < 400)
									.sorted(comparingInt(Dish::getCalories))
									.map(Dish::getName)
									.collect(Collectors.toList());
			
			for (String s : result) {
				System.out.println(s);
			}

		}
	}
}
