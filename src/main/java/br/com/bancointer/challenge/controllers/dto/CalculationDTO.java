package br.com.bancointer.challenge.controllers.dto;

import br.com.bancointer.challenge.domain.Calculation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalculationDTO {

    private Long input;
    private Integer result;

    public static CalculationDTO toDTO(final Calculation calculation) {
        return new CalculationDTO(calculation.getInput(), calculation.getResult());
    }
}
