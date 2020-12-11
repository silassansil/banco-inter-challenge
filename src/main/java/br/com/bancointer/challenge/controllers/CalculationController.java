package br.com.bancointer.challenge.controllers;

import br.com.bancointer.challenge.domain.Calculation;
import br.com.bancointer.challenge.service.CalculateUniqueDigitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculations")
@RequiredArgsConstructor
public class CalculationController {

    private final CalculateUniqueDigitService calculateUniqueDigitService;

    @GetMapping("/{input}")
    public ResponseEntity<Integer> calculate(@PathVariable("input") String input) {
        final Calculation calculate = this.calculateUniqueDigitService.calculate(input);
        return ResponseEntity.ok(calculate.getResult());
    }

    @GetMapping("/{input}/frequency/{frequency}")
    public ResponseEntity<Integer> calculate(@PathVariable("input") String input,
                                             @PathVariable("frequency") Integer frequency) {
        final Calculation calculate = this.calculateUniqueDigitService.calculate(input, frequency);
        return ResponseEntity.ok(calculate.getResult());
    }
}
