package br.com.bancointer.challenge.service.impl;

import br.com.bancointer.challenge.domain.Calculation;
import br.com.bancointer.challenge.domain.User;
import br.com.bancointer.challenge.service.CalculateUniqueDigitService;
import br.com.bancointer.challenge.service.UserCalculationService;
import br.com.bancointer.challenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCalculationServiceImpl implements UserCalculationService {

    private final CalculateUniqueDigitService calculateUniqueDigitService;
    private final UserService userService;

    @Override
    public Optional<User> calculateAndUpdateUserData(String userId, String input) {
        final Calculation calculate = this.calculateUniqueDigitService.calculate(input);
        return this.userService.addCalculations(userId, calculate);
    }

    @Override
    public List<Calculation> findCalculationsByUserId(String userId) {
        return this.userService.findById(userId)
                .map(User::getCalculations)
                .orElse(new ArrayList<>());
    }
}
