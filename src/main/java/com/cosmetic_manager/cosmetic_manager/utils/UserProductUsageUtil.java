package com.cosmetic_manager.cosmetic_manager.utils;

import com.cosmetic_manager.cosmetic_manager.dto.UserProductUsageDto;
import com.cosmetic_manager.cosmetic_manager.exceptions.ProductNotFoundException;
import com.cosmetic_manager.cosmetic_manager.exceptions.UserNotFoundException;
import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.model.User;
import com.cosmetic_manager.cosmetic_manager.model.UserProductUsage;
import com.cosmetic_manager.cosmetic_manager.service.ProductService;
import com.cosmetic_manager.cosmetic_manager.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserProductUsageUtil {
    private final UserService userService;
    private final ProductService productService;

    public UserProductUsageUtil(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    public UserProductUsage fromUserProductUsageDtoToUserProductUsage(UserProductUsageDto userProductUsageDto) {
        User user = userService.getUserById(userProductUsageDto.getUserId());
        Product product = productService.getProductById(userProductUsageDto.getProductId());

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        if (product == null) {
            throw new ProductNotFoundException("Product not found");
        }

        return UserProductUsage.builder()
                .id(new UserProductUsagePK(user, product))
                .usageCount(userProductUsageDto.getUsageCount())
                .PAO(userProductUsageDto.getPAO())
                .openingDate(userProductUsageDto.getOpeningDate())
                .build();

    }

}
