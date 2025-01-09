package Not.Delivered.shop.dto;

import Not.Delivered.common.entity.Address;
import Not.Delivered.menu.dto.MenuReadResponseDto;
import java.time.LocalTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ShopReadResponseDto {

  private final String shopName;

  private final String introduce;

  private final Address address;

  private final String phoneNumber;

  private final LocalTime openTime;

  private final LocalTime closeTime;

  private final Long minOrderPrice;

  private final List<MenuReadResponseDto> menuList;

  @Builder
  public ShopReadResponseDto(
      String shopName,
      String introduce,
      Address address,
      String phoneNumber,
      LocalTime openTime,
      LocalTime closeTime,
      Long minOrderPrice,
      List<MenuReadResponseDto> menuList) {
    this.shopName = shopName;
    this.introduce = introduce;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.openTime = openTime;
    this.closeTime = closeTime;
    this.minOrderPrice = minOrderPrice;
    this.menuList = menuList;
  }
}
