package Not.Delivered.shop.service;

import Not.Delivered.shop.domain.Shop;
import Not.Delivered.shop.dto.ShopCreateRequestDto;
import Not.Delivered.shop.dto.ShopCreateResponseDto;
import Not.Delivered.shop.repository.ShopRepository;
import Not.Delivered.user.domain.User;
import Not.Delivered.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ShopService {

  private final UserRepository userRepository;
  private final ShopRepository shopRepository;
  final int MAX_SHOP_AMOUNT = 3;

  public ShopCreateResponseDto createShop(ShopCreateRequestDto dto) {
    int shopAmount = shopRepository.countByOwnerUser_UserId(dto.getUserId());

    if (shopAmount < MAX_SHOP_AMOUNT) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "가게는 최대 3개까지만 운영할 수 있습니다.");
    }

    User foundUser = userRepository.findById(dto.getUserId())
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    Shop newShop = Shop.builder()
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
}
