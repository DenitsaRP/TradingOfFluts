package com.flatexdegiro.fluts.model;

import static java.util.stream.Collectors.joining;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalculatedProfitResponse {

  List<SchuurProfitResult> schuurProfitResults;

  @Override
  public String toString() {
    StringBuilder responseBuilder = new StringBuilder();
    for (SchuurProfitResult result : schuurProfitResults) {
      responseBuilder.append("schuurs ").append(result.getSchuurNumber()).append("\n")
          .append("Maximum profit is ").append(result.getMaximumProfit()).append(".\n")
          .append("Number of fluts to buy: ")
          .append(result.getFlutsToBuy().stream()
              .map(Object::toString)
              .collect(joining(" ")))
          .append("\n");
    }
    return responseBuilder.toString().trim();
  }

}
