package com.erikssonherlo.user.infrastructure.adapter.input;

import com.erikssonherlo.common.application.anotation.InputPort;
import com.erikssonherlo.common.application.anotation.WebAdapter;
import com.erikssonherlo.common.application.response.ApiResponse;
import com.erikssonherlo.common.infraestructure.security.anotation.ValidateRole;
import com.erikssonherlo.user.application.dto.RegisterUserDTO;
import com.erikssonherlo.user.application.dto.ResetPasswordDTO;
import com.erikssonherlo.user.application.dto.UpdateUserDTO;
import com.erikssonherlo.user.domain.model.User;
import com.erikssonherlo.user.domain.model.enums.Role;
import com.erikssonherlo.user.infrastructure.port.input.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@EnableMethodSecurity
@Validated
@WebAdapter
public class UserController {
    private static Role role;
    private final CreateUser createUserUseCase;
    private final GenerateResetPasswordCode generateResetPasswordCodeUseCase;
    private final ResetPassword resetPasswordUseCase;
    private final GetUserInfo getUserInfoUseCase;
    /*private final GetUserInfo getUserInfoUseCase;
    private final GetAllUsers getAllUsersUseCase;
    private final GetAllUsersByRole getAllUsersByRoleUseCase;
    private final GetUserById getUserByIdUseCase;
    private final GetUserByEmail getUserByEmailUseCase;
    private final UpdateUser updateUserUseCase;
    private final DeleteUser deleteUserUseCase;
    private final ExistsById existsByIdUseCase;
    private final ExistsByEmail existsByEmailUseCase;
    private final ExistsByNit existsByNitUseCase;*/

    @PostMapping()
    //@ValidateRole({"ADMINISTRATOR"})
    public ApiResponse<User> saveUser (@RequestBody @Valid RegisterUserDTO user){
        return new ApiResponse(HttpStatus.OK.value(),"Success", HttpStatus.OK, createUserUseCase.createUser(user));
    }

    @PostMapping("{email}/auth-code")
    public ApiResponse<Boolean> generateAuthCode(@PathVariable String email) {
        return new ApiResponse(HttpStatus.OK.value(),"Success", HttpStatus.OK, generateResetPasswordCodeUseCase.generateResetPasswordCode(email));
    }

    @PostMapping("/{email}/reset-password")
    public ApiResponse<Boolean> resetPassword(@PathVariable String email, @RequestBody @Valid ResetPasswordDTO resetPasswordDTO) {
        return new ApiResponse(HttpStatus.OK.value(),"Success", HttpStatus.OK, resetPasswordUseCase.resetPassword(email, resetPasswordDTO));
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/info")
    @ValidateRole({"ADMINISTRATOR", "STORE", "WAREHOUSE", "SUPERVISOR"})
    public ApiResponse<User> getUserInfo(Authentication authentication){
        return new ApiResponse(HttpStatus.OK.value(),"Success", HttpStatus.OK,getUserInfoUseCase.getUserInfo(authentication));
    }


/*
    @GetMapping()
    @ValidateRole({"ADMINISTRATOR", "EMPLOYEE"})
    public ApiResponse<Page<User>> getAllUsers(Pageable pageable) {
        // Obtenemos los usuarios paginados a partir del caso de uso
        Page<User> users = getAllUsersUseCase.getAllUsers(pageable);
        return new ApiResponse<>(HttpStatus.OK.value(), "Success", HttpStatus.OK, users);
    }

    @GetMapping("/role")
    @ValidateRole({"ADMINISTRATOR", "EMPLOYEE"})
    public ApiResponse<Page<User>> getUsersByRole(@RequestParam Role role, Pageable pageable) {
        // Llamar al caso de uso pasando el role y pageable
        Page<User> users = getAllUsersByRoleUseCase.getAllUsersByRole(role, pageable);
        return new ApiResponse<>(HttpStatus.OK.value(), "Success", HttpStatus.OK, users);
    }

    @GetMapping("/{id}")
    @ValidateRole({"ADMINISTRATOR", "EMPLOYEE", "CLIENT"})
    public ApiResponse<User> getUserById(@PathVariable Long id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Success", HttpStatus.OK, getUserByIdUseCase.getUserById(id).orElse(null));
    }

    @GetMapping("/email/{email}")
    @ValidateRole({"ADMINISTRATOR", "EMPLOYEE"})
    public ApiResponse<User> getUserByEmail(@PathVariable String email) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Success", HttpStatus.OK, getUserByEmailUseCase.getUserByEmail(email).orElse(null));
    }
    // Endpoint para actualizar un usuario
    @PutMapping("/{id}")
    @ValidateRole({"ADMINISTRATOR", "EMPLOYEE", "CLIENT"})
    public ApiResponse<User> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserDTO userUpdateDTO) {
        User updatedUser = updateUserUseCase.updateUser(id, userUpdateDTO);
        return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully", HttpStatus.OK, updatedUser);
    }

    // Endpoint para eliminar (soft delete) un usuario
    @DeleteMapping("/{id}")
    @ValidateRole({"ADMINISTRATOR"})
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        deleteUserUseCase.deleteUser(id);
        return new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "User deleted successfully", HttpStatus.NO_CONTENT, null);
    }

    @RequestMapping(value = "/exists/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> existsById(@PathVariable Long id) {
        return existsByIdUseCase.existsById(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/exists/email/{email}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> existsByEmail(@PathVariable String email) {
        return existsByEmailUseCase.existsByEmail(email) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/exists/nit/{nit}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> existsByNit(@PathVariable String nit) {
        return existsByNitUseCase.existsByNit(nit) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }*/
}
