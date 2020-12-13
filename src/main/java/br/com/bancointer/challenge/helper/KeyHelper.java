package br.com.bancointer.challenge.helper;

import lombok.SneakyThrows;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static br.com.bancointer.challenge.helper.CipherConstants.ALGORITHM;
import static br.com.bancointer.challenge.helper.CipherConstants.KEY_SIZE;

public final class KeyHelper {

    @SneakyThrows
    public static KeyPair genKeyPair() {
        final KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM);
        generator.initialize(KEY_SIZE);
        return generator.generateKeyPair();
    }

    @SneakyThrows
    public static PrivateKey rebuildPrivateKey(final byte[] encodedKey) {
        final KeyFactory generatorKey = KeyFactory.getInstance(ALGORITHM);
        return generatorKey.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    @SneakyThrows
    public static PublicKey rebuildPublicKey(final String encodedKey) {
        final byte[] decode = Base64.getDecoder().decode(encodedKey);
        final KeyFactory generatorKey = KeyFactory.getInstance(ALGORITHM);
        return generatorKey.generatePublic(new X509EncodedKeySpec(decode));
    }
}
