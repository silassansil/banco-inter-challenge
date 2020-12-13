package br.com.bancointer.challenge.controllers.dto;

import br.com.bancointer.challenge.domain.User;
import br.com.bancointer.challenge.helper.KeyHelper;
import br.com.bancointer.challenge.helper.RsaCipher;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    @ApiModelProperty(value = "User Id")
    private String id;

    @NotBlank
    @ApiModelProperty(value = "User Name")
    private String name;

    @NotBlank
    @ApiModelProperty(value = "User Email")
    private String email;

    @ApiModelProperty(value = "User List of Calculation")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CalculationDTO> calculations;

    public User toDomain(final KeyPair keyPair) {
        return new User(
                RsaCipher.encrypt(this.name, keyPair.getPublic()),
                RsaCipher.encrypt(this.email, keyPair.getPublic()),
                keyPair.getPrivate().getEncoded());
    }

    public User toDomain(final String userId, final String publicKeyByte) {
        final PublicKey publicKey = KeyHelper.rebuildPublicKey(publicKeyByte);
        return new User(userId,
                RsaCipher.encrypt(this.name, publicKey),
                RsaCipher.encrypt(this.email, publicKey));
    }

    public static UserDTO toDTO(final User user) {
        final List<CalculationDTO> calculations = user.getCalculations()
                .stream()
                .map(CalculationDTO::toDTO)
                .collect(Collectors.toList());
        return new UserDTO(
                user.getId(),
                RsaCipher.decrypt(user.getName(), KeyHelper.rebuildPrivateKey(user.getPrivateKey())),
                RsaCipher.decrypt(user.getEmail(), KeyHelper.rebuildPrivateKey(user.getPrivateKey())),
                calculations);
    }
}
