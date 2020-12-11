package br.com.bancointer.challenge.controllers;

import br.com.bancointer.challenge.controllers.dto.CalculationDTO;
import br.com.bancointer.challenge.controllers.dto.UserDTO;
import br.com.bancointer.challenge.service.UserCalculationService;
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

    @PutMapping("{input}")
    public ResponseEntity<UserDTO> calculateByUser(@PathVariable("input") String input,
                                                   @PathVariable("userId") String userId) {

        return this.userCalculationService.calculateAndUpdateUserData(userId, input)
                .map(UserDTO::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CalculationDTO>> findCalculationByUserId(@PathVariable("userId") final String userId) {
        return ResponseEntity.ok(this.userCalculationService.findCalculationsByUserId(userId)
                .stream()
                .map(CalculationDTO::toDTO)
                .collect(Collectors.toList()));
    }
}
