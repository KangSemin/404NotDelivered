package Not.Delivered.purchase.dto;


import Not.Delivered.purchase.domain.PurchaseStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PurchaseStatusUpdateDto {

  @Setter
  @NotNull(message = "주문 상태는 필수입니다.")
  private PurchaseStatus purchaseStatus;

  // 필요하면 추가 메서드나 필드를 정의할 수 있습니다.
}
