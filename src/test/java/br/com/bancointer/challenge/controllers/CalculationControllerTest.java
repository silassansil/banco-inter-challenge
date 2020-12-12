package br.com.bancointer.challenge.controllers;

import br.com.bancointer.challenge.service.CalculateUniqueDigitService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static br.com.bancointer.challenge.util.TestsUtil.buildCalculation;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CalculationController.class)
class CalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculateUniqueDigitService calculateUniqueDigitService;

    @Test
    @DisplayName("Calculation Simple Input")
    void calculateOnlyOneInput() throws Exception {

        when(this.calculateUniqueDigitService.calculate(anyString()))
                .thenReturn(buildCalculation());

        this.mockMvc.perform(get("/calculations/9875")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));

        verify(this.calculateUniqueDigitService).calculate(anyString());
    }

    @Test
    @DisplayName("Calculation With Input And Frequency")
    void calculateWithInputAndFrequency() throws Exception {
        when(this.calculateUniqueDigitService.calculate(anyString(), any()))
                .thenReturn(buildCalculation());

        this.mockMvc.perform(get("/calculations/9875/frequency/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));

        verify(this.calculateUniqueDigitService).calculate(anyString(), anyInt());
    }
}