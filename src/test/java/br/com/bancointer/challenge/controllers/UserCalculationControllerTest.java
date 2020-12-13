package br.com.bancointer.challenge.controllers;

import br.com.bancointer.challenge.domain.Calculation;
import br.com.bancointer.challenge.domain.User;
import br.com.bancointer.challenge.helper.KeyHelper;
import br.com.bancointer.challenge.helper.RsaCipher;
import br.com.bancointer.challenge.service.UserCalculationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static br.com.bancointer.challenge.util.TestsUtil.buildCalculation;
import static br.com.bancointer.challenge.util.TestsUtil.buildUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserCalculationController.class)
class UserCalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserCalculationService userCalculationService;

    @Test
    @DisplayName("Perform Calculation With Defined User")
    void calculateByUser() throws Exception {
        final Calculation calculation = buildCalculation();
        final Optional<User> user = buildUser(calculation);
        final User bodyExpected = user.orElse(new User());

        Mockito.when(this.userCalculationService.calculateAndUpdateUserData(anyString(), any()))
                .thenReturn(user);

        this.mockMvc.perform(put("/users/USER_ID/calculations/9875")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bodyExpected.getId()))
                .andExpect(jsonPath("$.name").value(RsaCipher.decrypt(bodyExpected.getName(), KeyHelper.rebuildPrivateKey(bodyExpected.getPrivateKey()))))
                .andExpect(jsonPath("$.email").value(RsaCipher.decrypt(bodyExpected.getEmail(), KeyHelper.rebuildPrivateKey(bodyExpected.getPrivateKey()))));

        verify(this.userCalculationService).calculateAndUpdateUserData(anyString(), anyString());
    }

    @Test
    @DisplayName("Perform Calculation When User Is Not Found")
    void calculateByUserWhenUserNotFound() throws Exception {
        Mockito.when(this.userCalculationService.calculateAndUpdateUserData(anyString(), any()))
                .thenReturn(Optional.empty());

        this.mockMvc.perform(put("/users/USER_ID/calculations/9875")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Find All Calculation By User Id")
    void findCalculationByUserId() throws Exception {
        final Calculation calculation = buildCalculation();

        Mockito.when(this.userCalculationService.findCalculationsByUserId(anyString()))
                .thenReturn(Collections.singletonList(calculation));

        this.mockMvc.perform(get("/users/USER_ID/calculations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].input").value(calculation.getInput()))
                .andExpect(jsonPath("$[0].result").value(calculation.getResult()));

    }
}