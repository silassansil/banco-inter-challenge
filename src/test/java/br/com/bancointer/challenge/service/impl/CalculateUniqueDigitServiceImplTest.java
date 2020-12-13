package br.com.bancointer.challenge.service.impl;

import br.com.bancointer.challenge.domain.Calculation;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.IntStream;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
class CalculateUniqueDigitServiceImplTest {

    @InjectMocks
    private CalculateUniqueDigitServiceImpl uniqueDigit;

    @Test
    @DisplayName("Calculate When String Has Only One Digit")
    void calculateWhenStringHasUniqDigit() {
        Calculation calculation = this.uniqueDigit.calculate("9");
        Assert.assertEquals("Input: 9 -- Result: 9", calculation.toString());
    }

    @Test
    @DisplayName("Calculate When String Has More Than One Digit")
    void calculateWhenStringHasMoreThanOneDigit() {
        Calculation calculation = this.uniqueDigit.calculate("9875");
        Assert.assertEquals("Input: 9875 -- Result: 2", calculation.toString());
    }

    @Test
    @DisplayName("Retrieve Data From Cache")
    void retrieveDataFromCache() {
        IntStream.range(100, 110)
                .forEach(x -> this.uniqueDigit.calculate(String.valueOf(x)));

        IntStream.range(100, 110)
                .forEach(x -> this.uniqueDigit.calculate(String.valueOf(x)));
    }

    @Test
    @DisplayName("Calculate When Has Two Parameters String And One Frequency")
    void calculateWhenHasTwoParametersStringOneFrequency() {
        Calculation calculation = this.uniqueDigit.calculate("9", 1);
        Assert.assertEquals("Input: 9 -- Result: 9", calculation.toString());
    }

    @Test
    @DisplayName("Calculate When Has Two Parameters String And Four Frequency")
    void calculateWhenHasTwoParametersStringFourFrequency() {
        Calculation calculation = this.uniqueDigit.calculate("9875", 4);
        Assert.assertEquals("Input: 9875987598759875 -- Result: 8", calculation.toString());
    }
}