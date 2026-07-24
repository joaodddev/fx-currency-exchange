package br.com.joaodddev.fxcurrencyexchange.web.controller

import br.com.joaodddev.fxcurrencyexchange.application.usecase.LoginUserUseCase
import br.com.joaodddev.fxcurrencyexchange.application.usecase.RegisterUserUseCase
import br.com.joaodddev.fxcurrencyexchange.web.dto.AuthRequest
import br.com.joaodddev.fxcurrencyexchange.web.dto.AuthResponse
import br.com.joaodddev.fxcurrencyexchange.web.dto.RegisterRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Register and login endpoints")
class AuthController(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase
) {

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    fun register(@RequestBody @Valid request: RegisterRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(registerUserUseCase.execute(request))

    @PostMapping("/login")
    @Operation(summary = "Authenticate and get JWT token")
    fun login(@RequestBody @Valid request: AuthRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.ok(loginUserUseCase.execute(request))
}