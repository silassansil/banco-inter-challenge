package br.com.bancointer.challenge.validate.impl;

import br.com.bancointer.challenge.validate.ValidateInputStringEmpty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ValidateInputStringEmptyImpl implements ValidateInputStringEmpty {

    @Override
    public void validate(String input) {
        if (StringUtils.isBlank(input))
            throw new IllegalArgumentException("The entry must have at least parameters");
    }
}
