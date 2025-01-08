package Not.Delivered.purchase.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PurchaseCreateDto {

  @NotNull(message = "가게 ID는 필수입니다.")
  @Setter
  private Long shopId;

  @NotNull(message = "메뉴 ID는 필수입니다.")
  @Setter
  private Long menuId;

}