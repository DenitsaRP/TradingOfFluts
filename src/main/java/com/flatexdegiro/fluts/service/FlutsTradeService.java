package com.flatexdegiro.fluts.service;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import com.flatexdegiro.fluts.model.SchuurCase;
import com.flatexdegiro.fluts.model.SchuurProfitResult;
import com.flatexdegiro.fluts.util.EntryComparator;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class FlutsTradeService {

  public static final int FLUTS_COMBINATION_TO_BUY_LIMIT = 10;
  public static final int FIXED_SELLING_PRICE = 10;

  public List<SchuurProfitResult> calculateSchuurCases(List<SchuurCase> schuurCases) {
    return schuurCases.stream()
        .map(schuurCase -> calculateProfitsFromSchuurCase(schuurCase.getSchuurNumber(),
            schuurCase.getNumberOfBoxesInPile(),
            schuurCase.getFlutBoxes()))
        .collect(toList());
  }

  private SchuurProfitResult calculateProfitsFromSchuurCase(int schuurCase, List<Integer> numberOfBoxes,
      List<List<Integer>> flutBoxes) {
    List<List<Entry<Integer, Integer>>> sortedProfits = new ArrayList<>(schuurCase);

    for (int i = 0; i < schuurCase; i++) {
      List<Entry<Integer, Integer>> sortedList = calculatePileProfits(numberOfBoxes.get(i), flutBoxes.get(i));
      sortedProfits.add(sortedList);
    }

    //extract the entries with max profit from each flut pile
    List<List<Entry<Integer, Integer>>> maxSortedProfits = sortedProfits.stream()
        .map(this::extractMaxProfits)
        .collect(toList());

    return new SchuurProfitResult(schuurCase,
        calculateMaxProfitForSchuur(maxSortedProfits),
        calculateNumberOfFlutsToBuy(maxSortedProfits));
  }

  private List<Entry<Integer, Integer>> calculatePileProfits(int numberOfBoxes, List<Integer> flutPrices) {
    int totalProfit = 0;
    List<Entry<Integer, Integer>> profits = new ArrayList<>(numberOfBoxes);

    for (int j = 0; j < numberOfBoxes; j++) {
      totalProfit += FIXED_SELLING_PRICE - flutPrices.get(j);
      int positionOfBoxInPile = j + 1;
      profits.add(Map.entry(positionOfBoxInPile, totalProfit));
    }

    profits.sort(new EntryComparator());

    return profits;
  }

  private List<Entry<Integer, Integer>> extractMaxProfits(List<Entry<Integer, Integer>> sortedList) {
    int maxProfit = sortedList.get(0).getValue();
    return sortedList.stream()
        .filter(profit -> profit.getValue() == maxProfit)
        .collect(toList());
  }

  private int calculateMaxProfitForSchuur(List<List<Entry<Integer, Integer>>> maxSortedProfits) {
    return maxSortedProfits.stream()
        .mapToInt(list -> list.get(0).getValue())
        .sum();
  }

  private Set<Integer> calculateNumberOfFlutsToBuy(List<List<Entry<Integer, Integer>>> maxSortedProfits) {
    List<Set<Integer>> flutBoxPositins = maxSortedProfits.stream()
        .map(list -> list.stream()
            .map(Entry::getKey)
            .collect(toSet())
        )
        .collect(toList());

    return Sets.cartesianProduct(flutBoxPositins).stream()
        .map(combination -> combination.stream()
            .mapToInt(Integer::intValue)
            .sum()
        )
        .sorted()
        .limit(FLUTS_COMBINATION_TO_BUY_LIMIT)
        .collect(toSet());
  }
}
