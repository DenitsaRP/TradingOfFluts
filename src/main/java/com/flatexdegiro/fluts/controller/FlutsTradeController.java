package com.flatexdegiro.fluts.controller;


import com.flatexdegiro.fluts.model.CalculatedProfitResponse;
import com.flatexdegiro.fluts.service.FlutsTradeService;
import com.flatexdegiro.fluts.util.InputParser;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/trade")
@AllArgsConstructor
public class FlutsTradeController {

  private final FlutsTradeService flutsTradeService;

  @PostMapping("/calculate")
  public ResponseEntity<String> calculateTrade(@RequestParam("file") MultipartFile file) throws IOException {
    String input = new String(file.getBytes());
    var cases = InputParser.parseInput(input);
    var result = flutsTradeService.calculateSchuurCases(cases);
    return ResponseEntity.ok(new CalculatedProfitResponse(result).toString());
  }

}
