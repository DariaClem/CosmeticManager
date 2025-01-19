package com.cosmetic_manager.cosmetic_manager.service;

import com.cosmetic_manager.cosmetic_manager.dto.UserProductUsageDto;
import com.cosmetic_manager.cosmetic_manager.exceptions.ProductNotFoundException;
import com.cosmetic_manager.cosmetic_manager.exceptions.UserNotFoundException;
import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.model.User;
import com.cosmetic_manager.cosmetic_manager.model.UserProductUsage;
import com.cosmetic_manager.cosmetic_manager.repository.UserProductUsageRepository;
import com.cosmetic_manager.cosmetic_manager.utils.UserProductUsagePK;
import com.cosmetic_manager.cosmetic_manager.utils.UserProductUsageUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProductUsageService {
    private final UserProductUsageRepository userProductUsageRepository;
    private final UserProductUsageUtil userProductUsageUtil;
    private final UserService userService;
    private final ProductService productService;

    public UserProductUsageService(UserProductUsageRepository userProductUsageRepository, UserProductUsageUtil userProductUsageUtil, UserService userService, ProductService productService) {
        this.userProductUsageRepository = userProductUsageRepository;
        this.userProductUsageUtil = userProductUsageUtil;
        this.userService = userService;
        this.productService = productService;
    }

    public List<UserProductUsage> getAllUserProductUsages(int user_id) {
        return userProductUsageRepository.findById_UserId(user_id);
    }

    public UserProductUsage getUserProductUsage(int user_id, int product_id) {
        User user = userService.getUserById(user_id);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        Product product = productService.getProductById(product_id);
        if (product == null) {
            throw new ProductNotFoundException("Product not found");
        }
        return userProductUsageRepository.findById(new UserProductUsagePK(user, product)).orElseThrow();
    }

    public UserProductUsage addUserProductUsage(UserProductUsageDto userProductUsageDto) {
        return userProductUsageRepository.save(userProductUsageUtil.fromUserProductUsageDtoToUserProductUsage(userProductUsageDto));
    }

    public UserProductUsage increaseProductUsage(int user_id, int product_id) {
        UserProductUsage userProductUsage = getUserProductUsage(user_id, product_id);

        userProductUsage.setUsageCount(userProductUsage.getUsageCount() + 1);
        userProductUsageRepository.save(userProductUsage);

        return userProductUsage;
    }

    public UserProductUsage editUserProductUsage(UserProductUsageDto userProductUsageDto) {
        int user_id = userProductUsageDto.getUserId();
        int product_id = userProductUsageDto.getProductId();

        UserProductUsage userProductUsage = getUserProductUsage(user_id, product_id);

        userProductUsage.setUsageCount(userProductUsageDto.getUsageCount());
        userProductUsage.setPAO(userProductUsageDto.getPAO());
        userProductUsage.setOpeningDate(userProductUsageDto.getOpeningDate());

        return userProductUsageRepository.save(userProductUsage);
    }

    public UserProductUsage deleteUserProductUsage(int user_id, int product_id) {
        UserProductUsage userProductUsage = getUserProductUsage(user_id, product_id);
        userProductUsageRepository.delete(userProductUsage);
        return userProductUsage;
    }

    public List<UserProductUsage> getExpiredProducts(int user_id) {
        return userProductUsageRepository.findExpiredProducts(user_id);
    }

    public List<UserProductUsage> getProductsThatWillExpire(int user_id, int days) {
        return userProductUsageRepository.findProductsThatWillExpire(user_id, days);
    }
}
