package br.com.bancointer.challenge.controllers;

import br.com.bancointer.challenge.domain.Calculation;
import br.com.bancointer.challenge.service.CalculateUniqueDigitService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Calculate Unique Digit")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Valid Result"),
    })
    @GetMapping("/{input}")
    public ResponseEntity<Integer> calculate(@ApiParam("Input Value To Calculate Unique Digit") @PathVariable("input") String input) {
        final Calculation calculate = this.calculateUniqueDigitService.calculate(input);
        return ResponseEntity.ok(calculate.getResult());
    }

    @ApiOperation(value = "Calculate Unique Digit With defined Frequency")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Valid Result"),
    })
    @GetMapping("/{input}/frequency/{frequency}")
    public ResponseEntity<Integer> calculate(@ApiParam("Input Value To Calculate Unique Digit") @PathVariable("input") String input,
                                             @ApiParam("Frequency That Value Will Repeated") @PathVariable("frequency") Integer frequency) {
        final Calculation calculate = this.calculateUniqueDigitService.calculate(input, frequency);
        return ResponseEntity.ok(calculate.getResult());
    }
}
