package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.AdminUrlResponse;
import com.anand.url_shortner.dto.AdminUserResponse;
import com.anand.url_shortner.entity.Role;
import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.entity.User;
import com.anand.url_shortner.repository.UrlRepository;
import com.anand.url_shortner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private AdminService adminService;

    private User user;

    private UrlMapping urlMapping;

    @BeforeEach
    void setUp() {

        user = User.builder()
                .id(1L)
                .name("Rahul")
                .email("rahul@gmail.com")
                .password("encoded-password")
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        urlMapping = new UrlMapping();
        urlMapping.setId(1L);
        urlMapping.setOriginalUrl("https://google.com");
        urlMapping.setShortCode("abcd");
        urlMapping.setTotalClicks(10L);
        urlMapping.setCreatedAt(LocalDateTime.now());
        urlMapping.setUser(user);
    }

    @Test
    @DisplayName("Get All Users Should Return User DTOs")
    void getAllUsers_shouldReturnUserDtos() {

        when(userRepository.findAll())
                .thenReturn(List.of(user));

        List<AdminUserResponse> response =
                adminService.getAllUsers();

        assertEquals(1, response.size());
        assertEquals(1L, response.get(0).getId());
        assertEquals("Rahul", response.get(0).getName());
        assertEquals(
                "rahul@gmail.com",
                response.get(0).getEmail()
        );
        assertEquals(
                Role.USER,
                response.get(0).getRole()
        );

        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Get All URLs Should Return URL DTOs")
    void getAllUrls_shouldReturnUrlDtos() {

        when(urlRepository.findAll())
                .thenReturn(List.of(urlMapping));

        List<AdminUrlResponse> response =
                adminService.getAllUrls();

        assertEquals(1, response.size());
        assertEquals(
                "https://google.com",
                response.get(0).getOriginalUrl()
        );
        assertEquals(
                "abcd",
                response.get(0).getShortCode()
        );
        assertEquals(
                10L,
                response.get(0).getTotalClicks()
        );
        assertEquals(
                1L,
                response.get(0).getUserId()
        );
        assertEquals(
                "rahul@gmail.com",
                response.get(0).getUserEmail()
        );

        verify(urlRepository).findAll();
    }

    @Test
    @DisplayName("Delete User Should Delete Existing User")
    void deleteUser_shouldDeleteExistingUser() {

        when(userRepository.existsById(1L))
                .thenReturn(true);

        adminService.deleteUser(1L);

        verify(userRepository).existsById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Delete User Should Throw Exception When User Not Found")
    void deleteUser_shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.existsById(99L))
                .thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> adminService.deleteUser(99L)
        );

        assertEquals(
                "User not found",
                exception.getMessage()
        );

        verify(userRepository).existsById(99L);
        verify(userRepository, never()).deleteById(99L);
    }

    @Test
    @DisplayName("Make Admin Should Change User Role To ADMIN")
    void makeAdmin_shouldChangeRoleToAdmin() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        adminService.makeAdmin(1L);

        assertEquals(Role.ADMIN, user.getRole());

        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Make User Should Change Role To USER")
    void makeUser_shouldChangeRoleToUser() {

        user.setRole(Role.ADMIN);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        adminService.makeUser(1L);

        assertEquals(Role.USER, user.getRole());

        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Make Admin Should Throw Exception When User Not Found")
    void makeAdmin_shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> adminService.makeAdmin(99L)
        );

        assertEquals(
                "User not found",
                exception.getMessage()
        );

        verify(userRepository).findById(99L);
    }

    @Test
    @DisplayName("Make User Should Throw Exception When User Not Found")
    void makeUser_shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> adminService.makeUser(99L)
        );

        assertEquals(
                "User not found",
                exception.getMessage()
        );

        verify(userRepository).findById(99L);
    }

    @Test
    @DisplayName("Get All Users Should Return Empty List")
    void getAllUsers_shouldReturnEmptyList() {

        when(userRepository.findAll())
                .thenReturn(List.of());

        List<AdminUserResponse> response =
                adminService.getAllUsers();

        assertFalse(response.iterator().hasNext());

        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Get All URLs Should Handle URL Without Owner")
    void getAllUrls_shouldHandleUrlWithoutOwner() {

        urlMapping.setUser(null);

        when(urlRepository.findAll())
                .thenReturn(List.of(urlMapping));

        List<AdminUrlResponse> response =
                adminService.getAllUrls();

        assertEquals(1, response.size());
        assertEquals(null, response.get(0).getUserId());
        assertEquals(null, response.get(0).getUserEmail());

        verify(urlRepository).findAll();
    }
}