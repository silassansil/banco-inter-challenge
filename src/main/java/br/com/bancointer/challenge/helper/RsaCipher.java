package br.com.bancointer.challenge.helper;

import lombok.SneakyThrows;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;

import static br.com.bancointer.challenge.helper.CipherConstants.ALGORITHM;

public final class RsaCipher {

    @SneakyThrows
    public static byte[] encrypt(final String value, final PublicKey publicKey) {
        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(value.getBytes());
    }

    @SneakyThrows
    public static String decrypt(final byte[] value, final PrivateKey privateKey) {
        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(value));
    }
}