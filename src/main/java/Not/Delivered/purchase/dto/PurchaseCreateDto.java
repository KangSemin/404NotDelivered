package Not.Delivered.purchase.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class PurchaseCreateDto {

  @Setter
  @NotNull(message = "메뉴 ID는 필수입니다.")
  private Long menuId;

  @Setter
  @NotNull(message = "가게 ID는 필수입니다.")
  private Long shopId;


}