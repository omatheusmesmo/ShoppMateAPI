package com.omatheusmesmo.shoppmate.list.service;

import com.omatheusmesmo.shoppmate.list.entity.ShoppList;
import com.omatheusmesmo.shoppmate.list.entity.ShoppListUserPermission;
import com.omatheusmesmo.shoppmate.list.repository.ShoppListUserPermissionRepository;
import com.omatheusmesmo.shoppmate.user.service.UserService;
import com.omatheusmesmo.shoppmate.user.entity.User;
import com.omatheusmesmo.shoppmate.shared.service.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppListUserPermissionServiceTest {

    @Mock
    private ShoppListUserPermissionRepository shoppListUserPermissionRepository;

    @Mock
    private ShoppListService shoppListService;

    @Mock
    private UserService userService;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private ShoppListUserPermissionService shoppListUserPermissionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addShoppListUserPermission_ValidPermission_ReturnsSavedPermission() {
        // Arrange
        ShoppListUserPermission permission = createSamplePermission();
        when(shoppListUserPermissionRepository.save(permission)).thenReturn(permission);

        // Act
        ShoppListUserPermission result = shoppListUserPermissionService.addShoppListUserPermission(permission);

        // Assert
        assertNotNull(result);
        assertEquals(permission, result);
        verify(userService, times(1)).isUserValid(permission.getUser());
        verify(shoppListService, times(1)).isListValid(permission.getShoppList());
        verify(auditService, times(1)).setAuditData(permission, true);
        verify(shoppListUserPermissionRepository, times(1)).save(permission);
    }

    @Test
    void isListValid_ValidPermission_NoExceptionThrown() {
        // Arrange
        ShoppListUserPermission permission = createSamplePermission();

        // Act & Assert
        assertDoesNotThrow(() -> shoppListUserPermissionService.isListValid(permission));
        verify(userService, times(1)).isUserValid(permission.getUser());
        verify(shoppListService, times(1)).isListValid(permission.getShoppList());
    }

    @Test
    void findListItem_ExistingPermission_ReturnsPermission() {
        // Arrange
        ShoppListUserPermission permission = createSamplePermission();
        when(shoppListUserPermissionRepository.findById(permission.getId())).thenReturn(Optional.of(permission));

        // Act
        Optional<ShoppListUserPermission> result = shoppListUserPermissionService.findListItem(permission);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(permission, result.get());
        verify(shoppListUserPermissionRepository, times(1)).findById(permission.getId());
    }

    @Test
    void findListItem_NonExistingPermission_ThrowsNoSuchElementException() {
        // Arrange
        ShoppListUserPermission permission = createSamplePermission();
        when(shoppListUserPermissionRepository.findById(permission.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> shoppListUserPermissionService.findListItem(permission));
        verify(shoppListUserPermissionRepository, times(1)).findById(permission.getId());
    }

    @Test
    void findListUserPermissionById_ExistingId_ReturnsPermission() {
        // Arrange
        Long id = 1L;
        ShoppListUserPermission permission = createSamplePermission();
        permission.setId(id);
        when(shoppListUserPermissionRepository.findById(id)).thenReturn(Optional.of(permission));

        // Act
        Optional<ShoppListUserPermission> result = shoppListUserPermissionService.findListUserPermissionById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(permission, result.get());
        verify(shoppListUserPermissionRepository, times(1)).findById(id);
    }

    @Test
    void findListUserPermissionById_NonExistingId_ThrowsNoSuchElementException() {
        // Arrange
        Long id = 1L;
        when(shoppListUserPermissionRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> shoppListUserPermissionService.findListUserPermissionById(id));
        verify(shoppListUserPermissionRepository, times(1)).findById(id);
    }

    @Test
    void removeList_ExistingId_DeletesPermission() {
        // Arrange
        Long id = 1L;
        ShoppListUserPermission permission = createSamplePermission();
        permission.setId(id);
        when(shoppListUserPermissionRepository.findById(id)).thenReturn(Optional.of(permission));

        // Act
        shoppListUserPermissionService.removeList(id);

        // Assert
        verify(shoppListUserPermissionRepository, times(1)).findById(id);
        verify(shoppListUserPermissionRepository, times(1)).deleteById(id);
    }

    @Test
    void removeList_NonExistingId_ThrowsNoSuchElementException() {
        // Arrange
        Long id = 1L;
        when(shoppListUserPermissionRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> shoppListUserPermissionService.removeList(id));
        verify(shoppListUserPermissionRepository, times(1)).findById(id);
        verify(shoppListUserPermissionRepository, never()).deleteById(any());
    }

    @Test
    void editList_ExistingPermission_ReturnsEditedPermission() {
        // Arrange
        ShoppListUserPermission permission = createSamplePermission();
        when(shoppListUserPermissionRepository.findById(permission.getId())).thenReturn(Optional.of(permission));
        when(shoppListUserPermissionRepository.save(permission)).thenReturn(permission);

        // Act
        ShoppListUserPermission result = shoppListUserPermissionService.editList(permission);

        // Assert
        assertNotNull(result);
        assertEquals(permission, result);
        verify(shoppListUserPermissionRepository, times(1)).findById(permission.getId());
        verify(userService, times(1)).isUserValid(permission.getUser());
        verify(shoppListService, times(1)).isListValid(permission.getShoppList());
        verify(shoppListUserPermissionRepository, times(1)).save(permission);
    }

    @Test
    void editList_NonExistingPermission_ThrowsNoSuchElementException() {
        // Arrange
        ShoppListUserPermission permission = createSamplePermission();
        when(shoppListUserPermissionRepository.findById(permission.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> shoppListUserPermissionService.editList(permission));
        verify(shoppListUserPermissionRepository, times(1)).findById(permission.getId());
        verify(shoppListUserPermissionRepository, never()).save(any());
    }

    @Test
    void findAll_MultiplePermissions_ReturnsAllPermissions() {
        // Arrange
        ShoppListUserPermission permission1 = createSamplePermission();
        ShoppListUserPermission permission2 = createSamplePermission();
        List<ShoppListUserPermission> permissions = Arrays.asList(permission1, permission2);
        when(shoppListUserPermissionRepository.findAll()).thenReturn(permissions);

        // Act
        List<ShoppListUserPermission> result = shoppListUserPermissionService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(permission1));
        assertTrue(result.contains(permission2));
        verify(shoppListUserPermissionRepository, times(1)).findAll();
    }

    private ShoppListUserPermission createSamplePermission() {
        ShoppListUserPermission permission = new ShoppListUserPermission();
        permission.setId(1L);
        User user = new User();
        user.setId(1L);
        permission.setUser(user);
        ShoppList list = new ShoppList();
        list.setId(1L);
        permission.setShoppList(list);
        permission.setCreatedAt(LocalDateTime.now());
        permission.setUpdatedAt(LocalDateTime.now());
        return permission;
    }
}