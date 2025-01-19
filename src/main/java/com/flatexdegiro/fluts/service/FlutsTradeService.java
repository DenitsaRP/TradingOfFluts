package com.flatexdegiro.fluts.service;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import com.flatexdegiro.fluts.model.SchuurCase;
import com.flatexdegiro.fluts.model.SchuurProfitResult;
import com.flatexdegiro.fluts.util.EntryComparator;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class FlutsTradeService {

  public static final int FLUTS_COMBINATION_TO_BUY_LIMIT = 10;
  public static final int FIXED_SELLING_PRICE = 10;

  public List<SchuurProfitResult> calculateSchuurCases(List<SchuurCase> schuurCases) {
   return schuurCases.stream().map(schuurCase ->
      calculateProfitsFromSchuurPiles(schuurCase.getSchuurNumber(), schuurCase.getNumberOfBoxesInPile(),
          schuurCase.getFlutBoxes())).collect(toList());
  }

  private SchuurProfitResult calculateProfitsFromSchuurPiles(int piles, List<Integer> boxes,
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

    return new SchuurProfitResult(piles, maxProfit, numberOfFlutsToBuy);
  }

  private List<List<Map.Entry<Integer, Integer>>> extractMaxProfitsPerPile(List<List<Map.Entry<Integer, Integer>>> sortedProfits) {
    List<List<Map.Entry<Integer, Integer>>> maxSortedProfits = new ArrayList<>(sortedProfits.size());

    sortedProfits.forEach(list -> maxSortedProfits.add(list.stream()
        .filter(entry -> entry.getValue().equals(list.get(0).getValue()))
        .collect(toList())));

    return maxSortedProfits;
  }

  private int calculateMaxProfit(List<List<Map.Entry<Integer, Integer>>> maxSortedProfits) {
    return maxSortedProfits.stream()
        .mapToInt(list -> list.get(0).getValue())
        .sum();
  }

  private Set<Integer> calculateNumberOfFlutsToBuy(List<Set<Integer>> sets) {
    return Sets.cartesianProduct(sets).stream()
        .map(x -> x.stream()
            .mapToInt(Integer::intValue)
            .sum())
        .sorted(Comparator.naturalOrder())
        .limit(FLUTS_COMBINATION_TO_BUY_LIMIT)
        .collect(toSet());
  }
}
