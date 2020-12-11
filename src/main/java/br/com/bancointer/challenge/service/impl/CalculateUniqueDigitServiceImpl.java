package br.com.bancointer.challenge.service.impl;

import br.com.bancointer.challenge.data.LRUCache;
import br.com.bancointer.challenge.domain.Calculation;
import br.com.bancointer.challenge.service.CalculateUniqueDigitService;
import br.com.bancointer.challenge.validate.ValidateInputOutOfRange;
import br.com.bancointer.challenge.validate.ValidateInputStringEmpty;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculateUniqueDigitServiceImpl implements CalculateUniqueDigitService {

    private final ValidateInputStringEmpty validateInputStringEmpty;
    private final ValidateInputOutOfRange validateInputOutOfRange;
    private final LRUCache<String, Integer> lruCache = new LRUCache<>(10);

    @Override
    public Calculation calculate(final String number) {
        this.validateInputStringEmpty.validate(number);

        final Integer value = this.lruCache.get(number);
        if (value != null) return new Calculation(Long.valueOf(number), value);

        final Calculation calculation = new Calculation(Long.valueOf(number));
        this.lruCache.put(number, calculation.getResult());

        return calculation;
    }

    @Override
    public Calculation calculate(final String number, final Integer frequency) {
        this.validateInputStringEmpty.validate(number);
        this.validateInputOutOfRange.validate(Integer.parseInt(number), 1, Math.pow(1_000_000, 10));
        this.validateInputOutOfRange.validate(frequency, 1, Math.pow(10, 5));

        final String repeat = StringUtils.repeat(number, frequency);
        return new Calculation(Long.valueOf(repeat));
    }
}
