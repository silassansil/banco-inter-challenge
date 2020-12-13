package br.com.bancointer.challenge.controllers;

import br.com.bancointer.challenge.controllers.dto.UserDTO;
import br.com.bancointer.challenge.domain.User;
import br.com.bancointer.challenge.helper.KeyHelper;
import br.com.bancointer.challenge.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Save user and generate public key")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User Created"),
            @ApiResponse(code = 500, message = "Invalid Public Key"),
    })
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

    @ApiOperation(value = "Update User Data by UserId")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User Updated"),
            @ApiResponse(code = 500, message = "Invalid Public Key"),
    })
    @PutMapping("{userId}")
    public ResponseEntity<UserDTO> update(@Valid @RequestBody final UserDTO userDTO,
                                          @ApiParam("User Id") @PathVariable("userId") final String userId,
                                          @RequestHeader(value = "x-challenge-public-key") final String publicKey) {
        final User userToSave = userDTO.toDomain(userId, publicKey);
        final User user = this.userService.saveOrUpdate(userToSave);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserDTO.toDTO(user));
    }

    @ApiOperation(value = "Save user and generate public key")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User Created"),
            @ApiResponse(code = 500, message = "Invalid Public Key"),
    })
    @DeleteMapping("{userId}")
    public ResponseEntity<Void> delete(@ApiParam("User Id") @PathVariable("userId") final String userId) {
        this.userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Delete user By UserId")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No contend"),
    })
    @GetMapping("{userId}")
    public ResponseEntity<?> findById(@ApiParam("User Id") @PathVariable("userId") final String userId) {
        return this.userService.findById(userId)
                .map(UserDTO::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Find All User In DataBase")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of All Users"),
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(this.userService.findAll()
                .map(UserDTO::toDTO)
                .collect(Collectors.toList()));
    }
}
