package com.omatheusmesmo.shoppmate.list.service;

import com.omatheusmesmo.shoppmate.list.entity.ShoppingList;
import com.omatheusmesmo.shoppmate.list.entity.ListPermission;
import com.omatheusmesmo.shoppmate.list.repository.ListPermissionRepository;
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

class ListPermissionServiceTest {

    @Mock
    private ListPermissionRepository ListPermissionRepository;

    @Mock
    private ShoppingListService shoppingListService;

    @Mock
    private UserService userService;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private ListPermissionService listPermissionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addListPermission_ValidPermission_ReturnsSavedPermission() {
        // Arrange
        ListPermission permission = createSamplePermission();
        when(ListPermissionRepository.save(permission)).thenReturn(permission);

        // Act
        ListPermission result = listPermissionService.addListPermission(permission);

        // Assert
        assertNotNull(result);
        assertEquals(permission, result);
        verify(userService, times(1)).isUserValid(permission.getUser());
        verify(shoppingListService, times(1)).isListValid(permission.getShoppingList());
        verify(auditService, times(1)).setAuditData(permission, true);
        verify(ListPermissionRepository, times(1)).save(permission);
    }

    @Test
    void isListValid_ValidPermission_NoExceptionThrown() {
        // Arrange
        ListPermission permission = createSamplePermission();

        // Act & Assert
        assertDoesNotThrow(() -> listPermissionService.isListValid(permission));
        verify(userService, times(1)).isUserValid(permission.getUser());
        verify(shoppingListService, times(1)).isListValid(permission.getShoppingList());
    }

    @Test
    void findListItem_ExistingPermission_ReturnsPermission() {
        // Arrange
        ListPermission permission = createSamplePermission();
        when(ListPermissionRepository.findById(permission.getId())).thenReturn(Optional.of(permission));

        // Act
        Optional<ListPermission> result = listPermissionService.findListItem(permission);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(permission, result.get());
        verify(ListPermissionRepository, times(1)).findById(permission.getId());
    }

    @Test
    void findListItem_NonExistingPermission_ThrowsNoSuchElementException() {
        // Arrange
        ListPermission permission = createSamplePermission();
        when(ListPermissionRepository.findById(permission.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> listPermissionService.findListItem(permission));
        verify(ListPermissionRepository, times(1)).findById(permission.getId());
    }

    @Test
    void findListUserPermissionById_ExistingId_ReturnsPermission() {
        // Arrange
        Long id = 1L;
        ListPermission permission = createSamplePermission();
        permission.setId(id);
        when(ListPermissionRepository.findById(id)).thenReturn(Optional.of(permission));

        // Act
        Optional<ListPermission> result = listPermissionService.findListUserPermissionById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(permission, result.get());
        verify(ListPermissionRepository, times(1)).findById(id);
    }

    @Test
    void findListUserPermissionById_NonExistingId_ThrowsNoSuchElementException() {
        // Arrange
        Long id = 1L;
        when(ListPermissionRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> listPermissionService.findListUserPermissionById(id));
        verify(ListPermissionRepository, times(1)).findById(id);
    }

    @Test
    void removeList_ExistingId_DeletesPermission() {
        // Arrange
        Long id = 1L;
        ListPermission permission = createSamplePermission();
        permission.setId(id);
        when(ListPermissionRepository.findById(id)).thenReturn(Optional.of(permission));

        // Act
        listPermissionService.removeList(id);

        // Assert
        verify(ListPermissionRepository, times(1)).findById(id);
        verify(ListPermissionRepository, times(1)).deleteById(id);
    }

    @Test
    void removeList_NonExistingId_ThrowsNoSuchElementException() {
        // Arrange
        Long id = 1L;
        when(ListPermissionRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> listPermissionService.removeList(id));
        verify(ListPermissionRepository, times(1)).findById(id);
        verify(ListPermissionRepository, never()).deleteById(any());
    }

    @Test
    void editList_ExistingPermission_ReturnsEditedPermission() {
        // Arrange
        ListPermission permission = createSamplePermission();
        when(ListPermissionRepository.findById(permission.getId())).thenReturn(Optional.of(permission));
        when(ListPermissionRepository.save(permission)).thenReturn(permission);

        // Act
        ListPermission result = listPermissionService.editList(permission);

        // Assert
        assertNotNull(result);
        assertEquals(permission, result);
        verify(ListPermissionRepository, times(1)).findById(permission.getId());
        verify(userService, times(1)).isUserValid(permission.getUser());
        verify(shoppingListService, times(1)).isListValid(permission.getShoppingList());
        verify(ListPermissionRepository, times(1)).save(permission);
    }

    @Test
    void editList_NonExistingPermission_ThrowsNoSuchElementException() {
        // Arrange
        ListPermission permission = createSamplePermission();
        when(ListPermissionRepository.findById(permission.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> listPermissionService.editList(permission));
        verify(ListPermissionRepository, times(1)).findById(permission.getId());
        verify(ListPermissionRepository, never()).save(any());
    }

    @Test
    void findAll_MultiplePermissions_ReturnsAllPermissions() {
        // Arrange
        ListPermission permission1 = createSamplePermission();
        ListPermission permission2 = createSamplePermission();
        List<ListPermission> permissions = Arrays.asList(permission1, permission2);
        when(ListPermissionRepository.findAll()).thenReturn(permissions);

        // Act
        List<ListPermission> result = listPermissionService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(permission1));
        assertTrue(result.contains(permission2));
        verify(ListPermissionRepository, times(1)).findAll();
    }

    private ListPermission createSamplePermission() {
        ListPermission permission = new ListPermission();
        permission.setId(1L);
        User user = new User();
        user.setId(1L);
        permission.setUser(user);
        ShoppingList list = new ShoppingList();
        list.setId(1L);
        permission.setShoppingList(list);
        permission.setCreatedAt(LocalDateTime.now());
        permission.setUpdatedAt(LocalDateTime.now());
        return permission;
    }
}