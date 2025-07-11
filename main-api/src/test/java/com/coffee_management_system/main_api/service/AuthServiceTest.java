package com.coffee_management_system.main_api.service;

import com.coffee_management_system.main_api.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_ShouldReturnJwtToken_WhenCredentialsAreValid() {
        String username = "testuser";
        String password = "testpass";
        String expectedToken = "jwt-token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(jwtUtil.generateToken(username)).thenReturn(expectedToken);

        String token = authService.login(username, password);

        assertEquals(expectedToken, token);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil).generateToken(username);
    }

    // ERROR CASES

    @Test
    void login_ShouldThrowBadCredentialsException_WhenPasswordIsWrong() {
        String username = "testuser";
        String password = "wrongpassword";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () ->
                authService.login(username, password));

        assertEquals("Invalid credentials", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    void login_ShouldThrowBadCredentialsException_WhenUsernameDoesNotExist() {
        String username = "nonexistentuser";
        String password = "password";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("User not found"));

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () ->
                authService.login(username, password));

        assertEquals("User not found", exception.getMessage());
        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    void login_ShouldThrowDisabledException_WhenAccountIsDisabled() {
        String username = "disableduser";
        String password = "password";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new DisabledException("Account is disabled"));

        DisabledException exception = assertThrows(DisabledException.class, () ->
                authService.login(username, password));

        assertEquals("Account is disabled", exception.getMessage());
        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    void login_ShouldThrowLockedException_WhenAccountIsLocked() {
        String username = "lockeduser";
        String password = "password";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new LockedException("Account is locked"));

        LockedException exception = assertThrows(LockedException.class, () ->
                authService.login(username, password));

        assertEquals("Account is locked", exception.getMessage());
        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    void login_ShouldThrowIllegalArgumentException_WhenUsernameIsNull() {
        String password = "password";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                authService.login(null, password));

        assertEquals("Username or password must not be empty", exception.getMessage());
        verify(authenticationManager, never()).authenticate(any());
        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    void login_ShouldThrowIllegalArgumentException_WhenUsernameIsEmpty() {
        String username = "";
        String password = "password";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                authService.login(username, password));

        assertEquals("Username or password must not be empty", exception.getMessage());
        verify(authenticationManager, never()).authenticate(any());
        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    void login_ShouldThrowIllegalArgumentException_WhenPasswordIsNull() {
        String username = "testuser";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                authService.login(username, null));

        assertEquals("Username or password must not be empty", exception.getMessage());
        verify(authenticationManager, never()).authenticate(any());
        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    void login_ShouldThrowIllegalArgumentException_WhenPasswordIsEmpty() {
        String username = "testuser";
        String password = "";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                authService.login(username, password));

        assertEquals("Username or password must not be empty", exception.getMessage());
        verify(authenticationManager, never()).authenticate(any());
        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    void login_ShouldThrowRuntimeException_WhenJwtGenerationFails() {
        String username = "testuser";
        String password = "testpass";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(jwtUtil.generateToken(username))
                .thenThrow(new RuntimeException("JWT generation failed"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                authService.login(username, password));

        assertEquals("JWT generation failed", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil).generateToken(username);
    }

    @Test
    void login_ShouldThrowAuthenticationException_WhenAuthenticationManagerFails() {
        String username = "testuser";
        String password = "testpass";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Authentication failed") {});

        AuthenticationException exception = assertThrows(AuthenticationException.class, () ->
                authService.login(username, password));

        assertEquals("Authentication failed", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    void login_ShouldThrowRuntimeException_WhenAuthenticationReturnsNull() {
        String username = "testuser";
        String password = "testpass";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                authService.login(username, password));

        assertEquals("Authentication returned null", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    void login_ShouldHandleUsernameWithSpecialCharacters() {
        String username = "user@domain.com";
        String password = "testpass";
        String expectedToken = "jwt-token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(jwtUtil.generateToken(username)).thenReturn(expectedToken);

        String token = authService.login(username, password);

        assertEquals(expectedToken, token);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil).generateToken(username);
    }
}
