package br.com.bancointer.challenge.util;

import br.com.bancointer.challenge.domain.Calculation;
import br.com.bancointer.challenge.domain.User;

import java.util.Optional;

public final class TestsUtil {

    private static final String INPUT = "9875";

    public static Calculation buildCalculation() {
        return new Calculation(Long.valueOf(INPUT));
    }

    public static Optional<User> buildUser(final Calculation calculation) {
        final User silas = new User("Silas".getBytes(), "asd@asd.com".getBytes());
        silas.addCalculationsOnUser(calculation);
        return Optional.of(silas);
    }
}
