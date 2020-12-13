package br.com.bancointer.challenge.util;

import br.com.bancointer.challenge.domain.Calculation;
import br.com.bancointer.challenge.domain.User;
import br.com.bancointer.challenge.helper.KeyHelper;
import br.com.bancointer.challenge.helper.RsaCipher;

import java.security.KeyPair;
import java.util.Optional;

public final class TestsUtil {

    private static final String INPUT = "9875";

    public static Calculation buildCalculation() {
        return new Calculation(Long.valueOf(INPUT));
    }

    public static Optional<User> buildUser(final Calculation calculation) {
        final KeyPair keyPair = KeyHelper.genKeyPair();

        final User silas = new User(
                RsaCipher.encrypt("Silas", keyPair.getPublic()),
                RsaCipher.encrypt("asd@asd.com", keyPair.getPublic()),
                keyPair.getPrivate().getEncoded());
        silas.addCalculationsOnUser(calculation);
        return Optional.of(silas);
    }
}
