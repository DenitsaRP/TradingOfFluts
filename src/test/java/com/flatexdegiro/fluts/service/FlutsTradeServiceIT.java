package com.flatexdegiro.fluts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.flatexdegiro.fluts.model.SchuurCase;
import com.flatexdegiro.fluts.model.SchuurProfitResult;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FlutsTradeServiceIT {

  @Autowired
  private FlutsTradeService flutsTradeService;

  @ParameterizedTest
  @MethodSource("provideSchuurCases")
  void calculateSchuurCasesTest(List<SchuurCase> schuurCases, List<SchuurProfitResult> expectedResults) {
    List<SchuurProfitResult> results = flutsTradeService.calculateSchuurCases(schuurCases);

    assertEquals(expectedResults.size(), results.size());

    for (int i = 0; i < results.size(); i++) {
      SchuurProfitResult actual = results.get(i);
      SchuurProfitResult expected = expectedResults.get(i);

      assertEquals(expected.getSchuurNumber(), actual.getSchuurNumber());
      assertEquals(expected.getMaximumProfit(), actual.getMaximumProfit());
      assertEquals(expected.getFlutsToBuy(), actual.getFlutsToBuy());
    }
  }

  private static Stream<Arguments> provideSchuurCases() {
    return Stream.of(
        Arguments.of(
            // Case 1
            List.of(
                new SchuurCase(1, List.of(3), List.of(List.of(2, 4, 1))),
                new SchuurCase(2, List.of(2, 3), List.of(List.of(1, 3), List.of(2, 2, 5)))
            ),
            List.of(
                new SchuurProfitResult(1, 23, Set.of(3)),
                new SchuurProfitResult(2, 37, Set.of(5))
            )
        ),
        Arguments.of(
            // Case 2
            List.of(
                new SchuurCase(1, List.of(2), List.of(List.of(5, 1))),
                new SchuurCase(2, List.of(2, 2), List.of(List.of(3, 2), List.of(1, 4)))
            ),
            List.of(
                new SchuurProfitResult(1, 14, Set.of(2)),
                new SchuurProfitResult(2, 30, Set.of(4))
            )
        ),
        Arguments.of(
            // Case 3
            List.of(
                new SchuurCase(1, List.of(6), List.of(List.of(12, 3, 10, 7, 16, 5))),
                new SchuurCase(2, List.of(5, 9), List.of(List.of(7, 3, 11, 9, 10), List.of(1, 2, 3, 4, 10, 16, 10, 4, 16)))
            ),
            List.of(
                new SchuurProfitResult(1, 8, Set.of(4)),
                new SchuurProfitResult(2, 40, Set.of(6, 7, 8, 9, 10, 12, 13))
            )
        )
    );
  }
}
