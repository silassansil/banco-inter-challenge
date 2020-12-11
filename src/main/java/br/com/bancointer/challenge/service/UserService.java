package br.com.bancointer.challenge.service;

import br.com.bancointer.challenge.domain.Calculation;
import br.com.bancointer.challenge.domain.User;

import java.util.Optional;
import java.util.stream.Stream;

public interface UserService {

    User saveOrUpdate(final User user);

    void delete(String userId);

    Optional<User> findById(String userId);

    Stream<User> findAll();

    Optional<User> addCalculations(final String userId, final Calculation calculation);
}
