package Not.Delivered.shop.service;

import Not.Delivered.menu.domain.Menu;
import Not.Delivered.menu.dto.MenuReadResponseDto;
import Not.Delivered.menu.repository.MenuRepository;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.shop.dto.ShopCreateRequestDto;
import Not.Delivered.shop.dto.ShopCreateResponseDto;
import Not.Delivered.shop.dto.ShopReadResponseDto;
import Not.Delivered.shop.repository.ShopRepository;
import Not.Delivered.user.domain.User;
import Not.Delivered.user.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ShopService {

  private final UserRepository userRepository;
  private final ShopRepository shopRepository;
  private final MenuRepository menuRepository;
  final int MAX_SHOP_AMOUNT = 3;

  public ShopCreateResponseDto createShop(Long userId, ShopCreateRequestDto dto) {
    int shopAmount = shopRepository.countByOwnerUserId(userId);

    if (shopAmount >= MAX_SHOP_AMOUNT) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "가게는 최대 3개까지만 운영할 수 있습니다.");
    }

    User foundUser =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    Shop newShop =
        Shop.builder()
            .ownerUser(foundUser)
            .shopName(dto.getShopName())
            .introduce(dto.getIntroduce())
            .address(dto.getAddress())
            .phoneNumber(dto.getPhoneNumber())
            .openTime(dto.getOpenTime())
            .closeTime(dto.getCloseTime())
            .minOrderPrice(dto.getMinOrderPrice())
            .build();

    Shop savedShop = shopRepository.save(newShop);

    return ShopCreateResponseDto.builder()
        .shopName(savedShop.getShopName())
        .introduce(savedShop.getIntroduce())
        .address(savedShop.getAddress())
        .phoneNumber(savedShop.getPhoneNumber())
        .openTime(savedShop.getOpenTime())
        .closeTime(savedShop.getCloseTime())
        .minOrderPrice(savedShop.getMinOrderPrice())
        .isClosing(savedShop.isClosing())
        .build();
  }

  public List<ShopReadResponseDto> findAllShop(String shopName) {
    List<Shop> foundShopList = shopRepository.findByShopName(shopName);

    if (foundShopList.isEmpty()) {
      throw new IllegalArgumentException("Shop not found");
    }

    return foundShopList.stream()
        .map(
            shop ->
                ShopReadResponseDto.builder()
                    .shopName(shop.getShopName())
                    .introduce(shop.getIntroduce())
                    .address(shop.getAddress())
                    .phoneNumber(shop.getPhoneNumber())
                    .openTime(shop.getOpenTime())
                    .closeTime(shop.getCloseTime())
                    .minOrderPrice(shop.getMinOrderPrice())
                    .menuList(Collections.emptyList())
                    .build())
        .toList();
  }

  public ShopReadResponseDto findByShopId(Long shopId) {
    Shop foundShop =
        shopRepository
            .findById(shopId)
            .orElseThrow(() -> new IllegalArgumentException("Shop not found"));

    List<Menu> foundMenuList = menuRepository.findAllByShop_ShopId(foundShop.getShopId());

    return ShopReadResponseDto.builder()
        .shopName(foundShop.getShopName())
        .introduce(foundShop.getIntroduce())
        .address(foundShop.getAddress())
        .phoneNumber(foundShop.getPhoneNumber())
        .openTime(foundShop.getOpenTime())
        .closeTime(foundShop.getCloseTime())
        .minOrderPrice(foundShop.getMinOrderPrice())
        .menuList(MenuReadResponseDto.getSellingMenuList(foundMenuList))
        .build();
  }
}
