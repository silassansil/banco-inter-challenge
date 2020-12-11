package br.com.bancointer.challenge.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Calculation {

    @Id
    private String id;

    private Long input;
    private Integer result;

    public Calculation(Long input) {
        this.id = UUID.randomUUID().toString();
        this.input = input;
        this.result = this.calculate(String.valueOf(this.input));
    }

    public Calculation(Long input, Integer result) {
        this.id = UUID.randomUUID().toString();
        this.input = input;
        this.result = result;
    }

    public Integer calculate(final String number) {
        if (number.length() == 1) return Integer.valueOf(number);
        int sum = Arrays.stream(number.split(""))
                .mapToInt(Integer::parseInt)
                .sum();
        return this.calculate(String.valueOf(sum));
    }

    @Override
    public String toString() {
        return String.format("Input: %d -- Result: %d", input, result);
    }
}
