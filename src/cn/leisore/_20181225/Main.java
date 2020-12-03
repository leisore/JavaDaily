package cn.leisore._20181225;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {

	public static void main(String[] args) {

		List<Integer> list = Arrays.asList(new Integer[] { 3, 4, 1, 60, 8, 90 });

		list.stream().forEach( n -> System.out.print(n + " "));
		System.out.println();
		
		list.stream().sorted().forEach( n -> System.out.print(n + " "));
		System.out.println();
		
		list.stream().sorted(Integer::compareTo).forEach( n -> System.out.print(n + " "));
		System.out.println();

		Optional<Integer> min = list.stream().min((i, j) -> {
			return -i.compareTo(j);
		});
		if (min.isPresent()) {
			System.out.println(min.get());
		}
		
		list.stream().filter(i -> i % 2 == 0).filter(i -> i > 5).forEach( n -> System.out.print(n + " "));
	}

}
