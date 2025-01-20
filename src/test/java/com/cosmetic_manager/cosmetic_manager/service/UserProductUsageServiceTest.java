package com.cosmetic_manager.cosmetic_manager.service;

import com.cosmetic_manager.cosmetic_manager.dto.UserProductUsageDto;
import com.cosmetic_manager.cosmetic_manager.exceptions.ProductNotFoundException;
import com.cosmetic_manager.cosmetic_manager.exceptions.UserNotFoundException;
import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.model.User;
import com.cosmetic_manager.cosmetic_manager.model.UserProductUsage;
import com.cosmetic_manager.cosmetic_manager.repository.RoutineProductRepository;
import com.cosmetic_manager.cosmetic_manager.repository.UserProductUsageRepository;
import com.cosmetic_manager.cosmetic_manager.utils.UserProductUsagePK;
import com.cosmetic_manager.cosmetic_manager.utils.UserProductUsageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserProductUsageServiceTest {
    @Mock
    private UserProductUsageRepository userProductUsageRepository;

    @Mock
    private UserProductUsageUtil userProductUsageUtil;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @Mock
    private RoutineProductRepository routineProductRepository;

    @InjectMocks
    private UserProductUsageService userProductUsageService;

    private User user;
    private Product product;
    private UserProductUsagePK pk;
    private UserProductUsage usage;

    @BeforeEach
    void setUp() {
        int userId = 1;
        int productId = 1;
        user = new User();
        user.setId(userId);
        product = new Product();
        product.setId(productId);
        pk = new UserProductUsagePK(user, product);
        usage = new UserProductUsage(pk, 5, 12, new Date());
    }

    @Test
    @DisplayName("Get all user product usages")
    void getAllUserProductUsages() {
        // Arrange
        when(userService.getUserById(user.getId())).thenReturn(user);
        when(userProductUsageRepository.findById_UserId(user.getId())).thenReturn(List.of(usage));

        // Act
        var result = userProductUsageService.getAllUserProductUsages(user.getId());

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(usage, result.get(0));

        verify(userService, times(1)).getUserById(user.getId());
        verify(userProductUsageRepository, times(1)).findById_UserId(user.getId());
    }

    @Test
    @DisplayName("Get user product usage by user ID and product ID")
    void getUserProductUsage() {
        // Arrange
        when(userService.getUserById(user.getId())).thenReturn(user);
        when(productService.getProductById(product.getId())).thenReturn(product);
        when(userProductUsageRepository.findById(pk)).thenReturn(Optional.of(usage));

        // Act
        var result = userProductUsageService.getUserProductUsage(user.getId(), product.getId());

        // Assert
        assertNotNull(result);
        assertEquals(usage, result);

        verify(userService, times(1)).getUserById(user.getId());
        verify(productService, times(1)).getProductById(product.getId());
        verify(userProductUsageRepository, times(1)).findById(pk);
    }

    @Test
    @DisplayName("Get user product usage by user ID and product ID - user not found")
    void getUserProductUsageUserNotFound() {
        // Arrange
        when(userService.getUserById(user.getId())).thenReturn(null);

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userProductUsageService.getUserProductUsage(user.getId(), product.getId()));

        verify(userService, times(1)).getUserById(user.getId());
        verify(productService, never()).getProductById(anyInt());
        verify(userProductUsageRepository, never()).findById(any(UserProductUsagePK.class));
    }

    @Test
    @DisplayName("Get user product usage by user ID and product ID - product not found")
    void getUserProductUsageProductNotFound() {
        // Arrange
        when(userService.getUserById(user.getId())).thenReturn(user);
        when(productService.getProductById(product.getId())).thenReturn(null);

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> userProductUsageService.getUserProductUsage(user.getId(), product.getId()));

        verify(userService, times(1)).getUserById(user.getId());
        verify(productService, times(1)).getProductById(product.getId());
        verify(userProductUsageRepository, never()).findById(any(UserProductUsagePK.class));
    }

    @Test
    @DisplayName("Add user product usage")
    void addUserProductUsage() {
        // Arrange
        UserProductUsageDto userProductUsageDto = new UserProductUsageDto(user.getId(), product.getId(), 5, 12, new Date());
        when(userProductUsageUtil.fromUserProductUsageDtoToUserProductUsage(userProductUsageDto)).thenReturn(usage);
        when(userProductUsageRepository.findById(usage.getId())).thenReturn(Optional.empty());
        when(userProductUsageRepository.save(any(UserProductUsage.class))).thenReturn(usage);

        // Act
        UserProductUsage result = userProductUsageService.addUserProductUsage(userProductUsageDto);

        // Assert
        assertNotNull(result);
        assertEquals(usage.getId(), result.getId());
        assertEquals(usage.getUsageCount(), result.getUsageCount());
        assertEquals(usage.getPAO(), result.getPAO());
        assertEquals(usage.getOpeningDate(), result.getOpeningDate());

        verify(userProductUsageUtil, times(1)).fromUserProductUsageDtoToUserProductUsage(userProductUsageDto);
        verify(userProductUsageRepository, times(1)).findById(usage.getId());
        verify(userProductUsageRepository, times(1)).save(any(UserProductUsage.class));
    }

    @Test
    @DisplayName("Increase product usage")
    void increaseProductUsage() {
        // Arrange
        when(userService.getUserById(user.getId())).thenReturn(user);
        when(productService.getProductById(product.getId())).thenReturn(product);
        when(userProductUsageRepository.findById(pk)).thenReturn(Optional.of(usage));

        // Act
        UserProductUsage result = userProductUsageService.increaseProductUsage(user.getId(), product.getId());

        // Assert
        assertNotNull(result);
        assertEquals(6, result.getUsageCount());

        verify(userService, times(1)).getUserById(user.getId());
        verify(productService, times(1)).getProductById(product.getId());
        verify(userProductUsageRepository, times(1)).findById(pk);
        verify(userProductUsageRepository, times(1)).save(usage);
    }

    @Test
    @DisplayName("Edit user product usage")
    void editUserProductUsage() {
        // Arrange
        UserProductUsageDto userProductUsageDto = new UserProductUsageDto(user.getId(), product.getId(), 10, 24, new Date());
        when(userService.getUserById(user.getId())).thenReturn(user);
        when(productService.getProductById(product.getId())).thenReturn(product);
        when(userProductUsageRepository.findById(pk)).thenReturn(Optional.of(usage));
        when(userProductUsageRepository.save(any(UserProductUsage.class))).thenReturn(usage);

        // Act
        UserProductUsage result = userProductUsageService.editUserProductUsage(userProductUsageDto);

        // Assert
        assertNotNull(result);
        assertEquals(userProductUsageDto.getUsageCount(), result.getUsageCount());
        assertEquals(userProductUsageDto.getPAO(), result.getPAO());
        assertEquals(userProductUsageDto.getOpeningDate(), result.getOpeningDate());

        verify(userService, times(1)).getUserById(user.getId());
        verify(productService, times(1)).getProductById(product.getId());
        verify(userProductUsageRepository, times(1)).findById(pk);
        verify(userProductUsageRepository, times(1)).save(usage);
    }

    @Test
    @DisplayName("Delete user product usage")
    void deleteUserProductUsage() {
        // Arrange
        when(userService.getUserById(user.getId())).thenReturn(user);
        when(productService.getProductById(product.getId())).thenReturn(product);
        when(userProductUsageRepository.findById(pk)).thenReturn(Optional.of(usage));

        // Act
        UserProductUsage result = userProductUsageService.deleteUserProductUsage(user.getId(), product.getId());

        // Assert
        assertNotNull(result);
        assertEquals(usage, result);

        verify(userService, times(1)).getUserById(user.getId());
        verify(productService, times(1)).getProductById(product.getId());
        verify(userProductUsageRepository, times(1)).findById(pk);
        verify(routineProductRepository, times(1)).deleteRoutineProductsById_ProductId(product.getId());
        verify(userProductUsageRepository, times(1)).delete(usage);
    }

    @Test
    @DisplayName("Get expired products")
    void getExpiredProducts() {
        // Arrange
        when(userService.getUserById(user.getId())).thenReturn(user);
        when(userProductUsageRepository.findExpiredProducts(user.getId())).thenReturn(List.of(usage));

        // Act
        var result = userProductUsageService.getExpiredProducts(user.getId());

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(usage, result.get(0));

        verify(userService, times(1)).getUserById(user.getId());
        verify(userProductUsageRepository, times(1)).findExpiredProducts(user.getId());
    }

    @Test
    @DisplayName("Get products that will expire within a specified number of days")
    void getProductsThatWillExpire() {
        // Arrange
        int days = 30;
        when(userService.getUserById(user.getId())).thenReturn(user);
        when(userProductUsageRepository.findProductsThatWillExpire(user.getId(), days)).thenReturn(List.of(usage));

        // Act
        var result = userProductUsageService.getProductsThatWillExpire(user.getId(), days);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(usage, result.get(0));

        verify(userService, times(1)).getUserById(user.getId());
        verify(userProductUsageRepository, times(1)).findProductsThatWillExpire(user.getId(), days);
    }
}