package Not.Delivered.shop.dto;

import Not.Delivered.common.entity.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ShopCreateRequestDto {

  @NotNull(message = "사용자 ID는 필수 입력 값입니다.")
  private final Long userId;

  @NotBlank(message = "가게 이름은 필수 입력 값입니다.")
  private final String shopName;

  private final String introduce;

  @Valid
  private final Address address;

  private final String phoneNumber;

  @NotNull(message = "개점 시간은 필수 입력 값입니다.")
  private final LocalTime openTime;

  @NotNull(message = "폐점 시간은 필수 입력 값입니다.")
  private final LocalTime closeTime;

  @NotNull(message = "최소 주문 금액은 필수 입력 값입니다.")
  private final Long minOrderPrice;

  @Builder
  public ShopCreateRequestDto(Long userId, String shopName, String introduce, Address address,
      String phoneNumber, LocalTime openTime, LocalTime closeTime, Long minOrderPrice) {
    this.userId = userId;
    this.shopName = shopName;
    this.introduce = introduce;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.openTime = openTime;
    this.closeTime = closeTime;
    this.minOrderPrice = minOrderPrice;
  }
}
