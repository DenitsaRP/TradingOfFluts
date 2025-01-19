package com.flatexdegiro.fluts.util;

import com.flatexdegiro.fluts.exception.InvalidInputException;
import com.flatexdegiro.fluts.model.SchuurCase;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputParser {

  public static List<SchuurCase> parseInput(String input) {
    List<SchuurCase> cases = new ArrayList<>();
    try (Scanner scanner = new Scanner(input)) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();

        if (!line.matches("\\d+")) {
          throw new InvalidInputException("Invalid input: Expected an integer describing pile of fluts in "
              + "schuur, greater than 0, or more piles of fluts in schuur are entered.");
        }

        int schuurNumber = Integer.parseInt(line);

        if (schuurNumber == 0) {
          break;
        }

        List<List<Integer>> flutBoxesInSchuur = new ArrayList<>();
        List<Integer> numberOfBoxesInPile = new ArrayList<>();

        int actualSchuurRows = 0;
        for (int i = 0; i < schuurNumber; i++) {
          if (!scanner.hasNextLine()) {
            throw new InvalidInputException("Invalid input: Insufficient lines for schuur descriptions. Expected "
                + schuurNumber + " rows but found " + actualSchuurRows);
          }

          actualSchuurRows++;
          String pileLine = scanner.nextLine().trim();
          String[] parts = pileLine.split("\\s+");

          if (parts.length == 0 || !parts[0].matches("\\d+")) {
            throw new InvalidInputException("Invalid input: Expected an integer for number of boxes in pile.");
          }

          int numberOfFluts = Integer.parseInt(parts[0]);

          if (numberOfFluts != parts.length - 1) {
            throw new InvalidInputException("Invalid input: Number of fluts boxes does not match the count "
                + "of given flut prices.");
          }

          List<Integer> flutBoxes = new ArrayList<>();
          for (int j = 1; j < parts.length; j++) {
            if (!parts[j].matches("\\d+")) {
              throw new InvalidInputException("Invalid input: Flut price is not a positive integer.");
            }
            flutBoxes.add(Integer.parseInt(parts[j]));
          }

          numberOfBoxesInPile.add(flutBoxes.size());
          flutBoxesInSchuur.add(flutBoxes);
        }

        cases.add(new SchuurCase(schuurNumber, numberOfBoxesInPile, flutBoxesInSchuur));
      }
    }
    return cases;
  }
}
