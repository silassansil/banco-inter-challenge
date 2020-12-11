package br.com.bancointer.challenge.validate.impl;

import br.com.bancointer.challenge.validate.ValidateInputOutOfRange;
import org.springframework.stereotype.Component;

@Component
public class ValidateInputOutOfRangeImpl implements ValidateInputOutOfRange {

    @Override
    public void validate(Integer input, Integer start, Double end) {
        if (input < start || input > end)
            throw new IllegalArgumentException("Input Number out of range");
    }
}
