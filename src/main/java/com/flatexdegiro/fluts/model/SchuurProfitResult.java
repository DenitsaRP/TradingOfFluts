package com.flatexdegiro.fluts.model;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SchuurProfitResult {

  private int schuurNumber;
  private int maximumProfit;
  private Set<Integer> flutsToBuy;
}
