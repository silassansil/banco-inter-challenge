package br.com.bancointer.challenge.service;

import br.com.bancointer.challenge.domain.Calculation;

public interface CalculateUniqueDigitService {

    Calculation calculate(final String number);

    Calculation calculate(final String number, final Integer frequency);
}
