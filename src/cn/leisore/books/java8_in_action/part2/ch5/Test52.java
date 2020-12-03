package cn.leisore.books.java8_in_action.part2.ch5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Test52 {

	public static void main(String[] args) {
		Trader raoul = new Trader("Raoul", "Cambridge");
		Trader mario = new Trader("Mario", "Milan");
		Trader alan = new Trader("Alan", "Cambridge");
		Trader brian = new Trader("Brian", "Cambridge");
		
		List<Transaction> transactions = Arrays.asList(
				new Transaction(brian, 2011, 300),
				new Transaction(raoul, 2012, 1000),
				new Transaction(raoul, 2011, 400),
				new Transaction(mario, 2012, 710),
				new Transaction(mario, 2012, 700), 
				new Transaction(alan, 2012, 950));
		
		// 1
		transactions.stream()
						.filter(t -> t.getYear() == 2011)
						.sorted(Comparator.comparingInt(Transaction::getValue)).forEach(System.out::println);
		
		// 2
		transactions.stream().map(t -> t.getTrader().getCity()).distinct().forEach(System.out::println);
		
		// 3
		transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge")).map(Transaction::getTrader).sorted(Comparator.comparing(Trader::getName)).forEach(System.out::println);
		
		// 4
		boolean present = transactions.stream().filter(t -> t.getTrader().getCity().equals("Milan")).findAny().isPresent();
		System.out.println(present);
		
		// 5
		Integer reduce = transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge")).map(t -> t.getValue()).reduce(0, Integer::sum);
		System.out.println(reduce);
		
		// 6
		System.out.println(transactions.stream().map(t -> t.getValue()).max(Integer::compare).orElse(0));
		
		// 7
		System.out.println(transactions.stream().min(Comparator.comparingInt(Transaction::getValue)).orElse(null));
	}
}
