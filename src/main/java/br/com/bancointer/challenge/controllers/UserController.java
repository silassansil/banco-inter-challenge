package br.com.bancointer.challenge.controllers;

import br.com.bancointer.challenge.controllers.dto.UserDTO;
import br.com.bancointer.challenge.domain.User;
import br.com.bancointer.challenge.helper.KeyHelper;
import br.com.bancointer.challenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.KeyPair;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> save(@Valid @RequestBody final UserDTO userDTO) {
        final KeyPair keyPair = KeyHelper.genKeyPair();
        final User userToSave = userDTO.toDomain(keyPair);
        final User user = this.userService.saveOrUpdate(userToSave);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("x-challenge-public-key", Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()))
                .body(UserDTO.toDTO(user));
    }

    @PutMapping("{userId}")
    public ResponseEntity<UserDTO> update(@Valid @RequestBody final UserDTO userDTO,
                                          @PathVariable("userId") final String userId,
                                          @RequestHeader(value = "x-challenge-public-key") final String publicKey) {
        final User userToSave = userDTO.toDomain(userId, publicKey);
        final User user = this.userService.saveOrUpdate(userToSave);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserDTO.toDTO(user));
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> delete(@PathVariable("userId") final String userId) {
        this.userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> findById(@PathVariable("userId") final String userId) {
        return this.userService.findById(userId)
                .map(UserDTO::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(this.userService.findAll()
                .map(UserDTO::toDTO)
                .collect(Collectors.toList()));
    }
}
