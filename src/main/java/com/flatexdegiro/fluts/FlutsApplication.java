package com.flatexdegiro.fluts;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import com.flatexdegiro.fluts.util.EntryComparator;
import com.google.common.collect.Sets;
import java.util.Comparator;
import java.util.Set;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@SpringBootApplication
public class FlutsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlutsApplication.class, args);
		//calculateProfitsFromSchuurPiles(2,List.of(5,9),
		// List.of(List.of(7,3,11,9,10), List.of(1,2,3,4,10,16,10,4,16)));
			//calculateProfitsFromSchuurPiles(1, List.of(6), List.of(List.of(12, 3, 10, 7, 16, 5)));
	}
/*
	public static void calculateProfitsFromSchuurPiles(int piles, List<Integer> boxes,
			List<List<Integer>> fluts) {
		List<List<Map.Entry<Integer, Integer>>> sortedProfits = new ArrayList<>(piles);

		for (int i = 0; i < piles; i++) {
			int numberOfBoxes = boxes.get(i);
			var sortedList = new ArrayList<Map.Entry<Integer, Integer>>(numberOfBoxes);
			var totalProfit = 0;
			for (int j = 1; j <= numberOfBoxes; j++) {
				var flutPrice = fluts.get(i).get(j - 1);
				totalProfit += FIXED_SELLING_PRICE - flutPrice;
				sortedList.add(Map.entry(j, totalProfit));
			}

			sortedList.sort(new EntryComparator());
			sortedProfits.add(sortedList);
		}

		var maxSortedProfits = extractMaxProfitsPerPile(sortedProfits);
		List<Set<Integer>> collected = maxSortedProfits.stream()
				.map(list -> list.stream().map(Map.Entry::getKey)
						.collect(toSet()))
				.collect(toList());

		var maxProfit = calculateMaxProfit(maxSortedProfits);
		var numberOfFlutsToBuy = calculateNumberOfFlutsToBuy(collected);

		System.out.println("Max profit: " + maxProfit);
		System.out.println("Number of fluts to buy: " + numberOfFlutsToBuy);


	}

	private static List<List<Map.Entry<Integer, Integer>>> extractMaxProfitsPerPile(List<List<Map.Entry<Integer, Integer>>> sortedProfits) {
		List<List<Map.Entry<Integer, Integer>>> maxSortedProfits = new ArrayList<>(sortedProfits.size());

		sortedProfits.forEach(list -> maxSortedProfits.add(list.stream()
				.filter(entry -> entry.getValue().equals(list.get(0).getValue()))
				.collect(toList())));

		return maxSortedProfits;
	}

	private static int calculateMaxProfit(List<List<Map.Entry<Integer, Integer>>> maxSortedProfits) {
		return maxSortedProfits.stream()
				.mapToInt(list -> list.get(0).getValue())
				.sum();
	}

	private static Set<Integer> calculateNumberOfFlutsToBuy(List<Set<Integer>> sets) {
		return Sets.cartesianProduct(sets).stream()
				.map(x -> x.stream()
						.mapToInt(Integer::intValue)
						.sum())
				.sorted(Comparator.naturalOrder())
				.limit(FLUTS_COMBINATION_TO_BUY_LIMIT)
				.collect(toSet());
	}

 */
}
