package br.com.bancointer.challenge.controllers;

import br.com.bancointer.challenge.controllers.dto.CalculationDTO;
import br.com.bancointer.challenge.controllers.dto.UserDTO;
import br.com.bancointer.challenge.service.UserCalculationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/{userId}/calculations")
@RequiredArgsConstructor
public class UserCalculationController {

    private final UserCalculationService userCalculationService;

    @ApiOperation(value = "Calculate Unique Digit By User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Valid Result And User Updated")
    })
    @PutMapping("{input}")
    public ResponseEntity<UserDTO> calculateByUser(@ApiParam("Input Value To Calculate Unique Digit") @PathVariable("input") String input,
                                                   @ApiParam("User Id That Will Save Result") @PathVariable("userId") String userId) {

        return this.userCalculationService.calculateAndUpdateUserData(userId, input)
                .map(UserDTO::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Find History Calculations By User Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Valid Result And User Updated")
    })
    @GetMapping
    public ResponseEntity<List<CalculationDTO>> findCalculationByUserId(@ApiParam("User Id To Search Calculations") @PathVariable("userId") final String userId) {
        return ResponseEntity.ok(this.userCalculationService.findCalculationsByUserId(userId)
                .stream()
                .map(CalculationDTO::toDTO)
                .collect(Collectors.toList()));
    }
}
