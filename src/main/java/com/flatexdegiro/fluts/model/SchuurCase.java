package com.flatexdegiro.fluts.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchuurCase {

  private int schuurNumber;
  private List<Integer> numberOfBoxesInPile;
  private List<List<Integer>> flutBoxes;
}
