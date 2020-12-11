package br.com.bancointer.challenge.service;

import br.com.bancointer.challenge.domain.Calculation;
import br.com.bancointer.challenge.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserCalculationService {

    Optional<User> calculateAndUpdateUserData(final String userId, final String input);

    List<Calculation> findCalculationsByUserId(final String userId);
}
