package Not.Delivered.purchase.dto;

import Not.Delivered.purchase.domain.PurchaseStatus;

import lombok.Getter;
import lombok.Setter;

@Setter // 테스트를 위해 임시 배치
@Getter
public class PurchaseDto {


  private Long purchaseId;
  private Long purchaseUserId;
  private Long deliveringUserId;
  private Long shopId;
  private Long menuId;
  private PurchaseStatus purchaseStatus;
  private boolean isCancelled;
  // 필요한 필드들을 추가로 선언
}