package Not.Delivered.shop.dto;

import Not.Delivered.common.entity.Address;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ShopCreateResponseDto {

  private final String shopName;

  private final String introduce;

  private final Address address;

  private final String phoneNumber;

  private final LocalTime openTime;

  private final LocalTime closeTime;

  private final Long minOrderPrice;

  private final boolean isClosing;

  @Builder
  public ShopCreateResponseDto(String shopName, String introduce, Address address,
      String phoneNumber, LocalTime openTime, LocalTime closeTime, Long minOrderPrice,
      boolean isClosing) {
    this.shopName = shopName;
    this.introduce = introduce;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.openTime = openTime;
    this.closeTime = closeTime;
    this.minOrderPrice = minOrderPrice;
    this.isClosing = isClosing;
  }
}
