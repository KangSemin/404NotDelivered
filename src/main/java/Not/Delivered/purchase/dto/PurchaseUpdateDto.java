package Not.Delivered.purchase.dto;

import Not.Delivered.purchase.domain.PurchaseStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class PurchaseUpdateDto {

  @Setter
  @NotNull(message = "주문 상태는 필수입니다.")
  private PurchaseStatus purchaseStatus;

}
