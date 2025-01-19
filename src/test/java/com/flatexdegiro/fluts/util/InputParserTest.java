package com.flatexdegiro.fluts.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.flatexdegiro.fluts.exception.InvalidInputException;
import com.flatexdegiro.fluts.model.SchuurCase;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InputParserTest {
  @Test
  void parseInputValidInputTest() {
    String input = """
            2
            3 2 4 6
            2 3 5
            1
            2 8 9
            0
            """;

    List<SchuurCase> schuurCases = InputParser.parseInput(input);

    assertEquals(2, schuurCases.size());

    SchuurCase firstCase = schuurCases.get(0);
    assertEquals(2, firstCase.getSchuurNumber());
    assertEquals(List.of(3, 2), firstCase.getNumberOfBoxesInPile());
    assertEquals(
        List.of(List.of(2, 4, 6), List.of(3, 5)),
        firstCase.getFlutBoxes()
    );

    SchuurCase secondCase = schuurCases.get(1);
    assertEquals(1, secondCase.getSchuurNumber());
    assertEquals(List.of(2), secondCase.getNumberOfBoxesInPile());
    assertEquals(List.of(List.of(8, 9)), secondCase.getFlutBoxes());
  }

  @Test
  void parseInputInvalidSchuurNumberTest() {
    String input = """
            -1
            0
            """;

    InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
      InputParser.parseInput(input);
    });

    assertEquals("Invalid input: Expected an integer describing pile of fluts in schuur, greater than 0, or more piles of fluts in schuur are entered.",
        exception.getMessage());
  }

  @Test
  void parseInputInvalidPileFormatTest() {
    String input = """
            1
            invalidPileFormat
            0
            """;

    InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
      InputParser.parseInput(input);
    });

    assertEquals("Invalid input: Expected an integer for number of boxes in pile.", exception.getMessage());
  }

  @Test
  void parseInputMismatchedFlutsCountTest() {
    String input = """
            1
            3 4 6
            0
            """;

    InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
      InputParser.parseInput(input);
    });

    assertEquals("Invalid input: Number of fluts boxes does not match the count of given flut prices.", exception.getMessage());
  }

  @Test
  void parseInputInvalidFlutPriceTest() {
    String input = """
            2
            3 a 4 6
            0
            """;

    InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
      InputParser.parseInput(input);
    });

    assertEquals("Invalid input: Flut price is not a positive integer.", exception.getMessage());
  }
}
