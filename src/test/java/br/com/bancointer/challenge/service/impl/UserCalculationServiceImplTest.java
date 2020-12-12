package br.com.bancointer.challenge.service.impl;

import br.com.bancointer.challenge.domain.Calculation;
import br.com.bancointer.challenge.domain.User;
import br.com.bancointer.challenge.service.CalculateUniqueDigitService;
import br.com.bancointer.challenge.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static br.com.bancointer.challenge.util.TestsUtil.buildCalculation;
import static br.com.bancointer.challenge.util.TestsUtil.buildUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
class UserCalculationServiceImplTest {

    @InjectMocks
    private UserCalculationServiceImpl userCalculationService;

    @Mock
    private CalculateUniqueDigitService calculateUniqueDigitService;

    @Mock
    private UserService userService;

    private static final String INPUT = "9875";

    @Test
    @DisplayName("Calculate And Update User Data When User Exists")
    public void calculateAndUpdateUserDataWhenUserExists() {
        final String userId = UUID.randomUUID().toString();
        final Calculation calculation = buildCalculation();
        when(this.calculateUniqueDigitService.calculate(anyString())).thenReturn(calculation);
        when(this.userService.addCalculations(userId, calculation)).thenReturn(buildUser(calculation));

        final Optional<User> user = this.userCalculationService.calculateAndUpdateUserData(userId, INPUT);

        verify(this.calculateUniqueDigitService).calculate(anyString());
        verify(this.userService).addCalculations(anyString(), any());

        user.ifPresent(u -> Assertions.assertTrue(u.getCalculations().contains(calculation)));
    }

    @Test
    @DisplayName("Calculate And Update User Data When User Not Exists")
    public void calculateAndUpdateUserDataWhenUserNotExists() {
        final String userId = UUID.randomUUID().toString();
        final Calculation calculation = buildCalculation();
        when(this.calculateUniqueDigitService.calculate(anyString())).thenReturn(calculation);
        when(this.userService.addCalculations(userId, calculation)).thenReturn(Optional.empty());

        final Optional<User> user = this.userCalculationService.calculateAndUpdateUserData(userId, INPUT);

        verify(this.calculateUniqueDigitService).calculate(anyString());
        verify(this.userService).addCalculations(anyString(), any());

        Assertions.assertThrows(
                NoSuchElementException.class,
                user::get,
                "No value present");
    }

    @Test
    @DisplayName("Find Calculations By User Id")
    public void findCalculationsByUserId() {
        final String userId = UUID.randomUUID().toString();
        final Calculation calculation = buildCalculation();
        when(this.userService.findById(userId)).thenReturn(buildUser(calculation));

        final List<Calculation> calculationsByUserId = this.userCalculationService.findCalculationsByUserId(userId);

        verify(this.userService).findById(anyString());
        Assertions.assertFalse(calculationsByUserId.isEmpty());
    }

    @Test
    @DisplayName("Find Calculations By User Id After Add New Calculation")
    public void findCalculationsByUserIdAfterAddNewCalculation() {
        final String userId = UUID.randomUUID().toString();
        final Calculation calculation = buildCalculation();
        when(this.userService.findById(userId)).thenReturn(buildUser(calculation));

        this.userCalculationService.calculateAndUpdateUserData(userId, INPUT);

        final List<Calculation> calculationsByUserId = this.userCalculationService.findCalculationsByUserId(userId);

        verify(this.userService).findById(anyString());
        Assertions.assertFalse(calculationsByUserId.isEmpty());
    }
}