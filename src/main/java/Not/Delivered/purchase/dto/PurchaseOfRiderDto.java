package Not.Delivered.purchase.dto;

import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.domain.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOfRiderDto {
  private Long purchaseId;
  private Long purchaseUserId;
  private Long shopId;
  private Long menuId;
  private PurchaseStatus purchaseStatus;
  private boolean isCancelled;
  private Long deliveringUserId;

  // 정적 팩토리 메서드에 @Builder 적용
  @Builder
  public static PurchaseOfRiderDto create(Long purchaseId, Long purchaseUserId, Long shopId,
      Long menuId, PurchaseStatus purchaseStatus,
      boolean isCancelled, Long deliveringUserId) {
    return new PurchaseOfRiderDto(purchaseId, purchaseUserId, shopId, menuId,
        purchaseStatus, isCancelled, deliveringUserId);
  }

  public static PurchaseOfRiderDto convertToDto(Purchase purchase) {
    return PurchaseOfRiderDto.builder()
        .purchaseId(purchase.getPurchaseId())
        .purchaseUserId(purchase.getPurchaseUser().getUserId())
        .shopId(purchase.getShop().getShopId())
        .menuId(purchase.getMenu().getMenuId())
        .purchaseStatus(purchase.getPurchaseStatus())
        .isCancelled(purchase.isCancelled())
        .deliveringUserId(
            purchase.getDeliveringUser() != null ? purchase.getDeliveringUser().getUserId() : null
        )
        .build();
  }
}