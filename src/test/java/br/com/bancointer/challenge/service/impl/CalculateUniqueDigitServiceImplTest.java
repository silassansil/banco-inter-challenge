package br.com.bancointer.challenge.service.impl;

import br.com.bancointer.challenge.domain.Calculation;
import br.com.bancointer.challenge.validate.impl.ValidateInputOutOfRangeImpl;
import br.com.bancointer.challenge.validate.impl.ValidateInputStringEmptyImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
class CalculateUniqueDigitServiceImplTest {

    @InjectMocks
    private CalculateUniqueDigitServiceImpl uniqueDigit;

    @Mock
    private ValidateInputStringEmptyImpl validateInputStringEmpty;

    @Mock
    private ValidateInputOutOfRangeImpl validateInputOutOfRange;

    @Test
    @DisplayName("Calculate When String Has Only One Digit")
    void calculateWhenStringHasUniqDigit() {
        Calculation calculation = this.uniqueDigit.calculate("9");

        Mockito.verify(this.validateInputStringEmpty).validate(anyString());
        Assert.assertEquals("Input: 9 -- Result: 9", calculation.toString());
    }

    @Test
    @DisplayName("Calculate When String Has More Than One Digit")
    void calculateWhenStringHasMoreThanOneDigit() {
        Calculation calculation = this.uniqueDigit.calculate("9875");

        Mockito.verify(this.validateInputStringEmpty).validate(anyString());
        Assert.assertEquals("Input: 9875 -- Result: 2", calculation.toString());
    }

    @Test
    @DisplayName("Retrieve Data From Cache")
    void retrieveDataFromCache() {
        IntStream.range(100, 110)
                .forEach(x -> this.uniqueDigit.calculate(String.valueOf(x)));

        IntStream.range(100, 110)
                .forEach(x -> this.uniqueDigit.calculate(String.valueOf(x)));

        Mockito.verify(this.validateInputStringEmpty, Mockito.times(20)).validate(anyString());
    }

    @Test
    @DisplayName("Calculate When Has Two Parameters String And One Frequency")
    void calculateWhenHasTwoParametersStringOneFrequency() {
        Calculation calculation = this.uniqueDigit.calculate("9", 1);

        Mockito.verify(this.validateInputStringEmpty).validate(anyString());
        Mockito.verify(this.validateInputOutOfRange, Mockito.times(2)).validate(any(), any(), any());
        Assert.assertEquals("Input: 9 -- Result: 9", calculation.toString());
    }

    @Test
    @DisplayName("Calculate When Has Two Parameters String And Four Frequency")
    void calculateWhenHasTwoParametersStringFourFrequency() {
        Calculation calculation = this.uniqueDigit.calculate("9875", 4);

        Mockito.verify(this.validateInputStringEmpty).validate(anyString());
        Mockito.verify(this.validateInputOutOfRange, Mockito.times(2)).validate(any(), any(), any());
        Assert.assertEquals("Input: 9875987598759875 -- Result: 8", calculation.toString());
    }

    @Test
    @DisplayName("Illegal Argument Number Must Not Be Empty")
    void illegalArgumentNumberMustNotBeEmpty() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> this.uniqueDigit.calculate(""),
                "The entry must have at least parameters");
    }

    @Test
    @DisplayName("Illegal Argument Number Out Of Range")
    void illegalArgumentInputNumberIsOutOfRange() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> this.uniqueDigit.calculate(String.valueOf(Math.pow(1_000_000, 50)), 5),
                "Input Number out of range");
    }
}