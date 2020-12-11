package br.com.bancointer.challenge.controllers.dto;

import br.com.bancointer.challenge.domain.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CalculationDTO> calculations;

    public User toDomain() {
        return new User(this.name, this.email);
    }

    public User toDomain(final String userId) {
        return new User(userId, this.name, this.email);
    }

    public static UserDTO toDTO(final User user) {
        final List<CalculationDTO> calculations = user.getCalculations()
                .stream()
                .map(CalculationDTO::toDTO)
                .collect(Collectors.toList());
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), calculations);
    }
}
