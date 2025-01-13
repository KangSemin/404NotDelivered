package Not.Delivered.purchase.dto;

import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.domain.PurchaseStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PurchaseDto {

  private final Long purchaseId;
  private final Long purchaseUserId;
  private final Long deliveringUserId;
  private final Long shopId;
  private final Long menuId;
  private final PurchaseStatus purchaseStatus;
  private final boolean isCancelled;

  @Builder
  public PurchaseDto(Long purchaseId, Long purchaseUserId, Long deliveringUserId, Long shopId,
      Long menuId, PurchaseStatus purchaseStatus, boolean isCancelled) {
    this.purchaseId = purchaseId;
    this.purchaseUserId = purchaseUserId;
    this.deliveringUserId = deliveringUserId;
    this.shopId = shopId;
    this.menuId = menuId;
    this.purchaseStatus = purchaseStatus;
    this.isCancelled = isCancelled;
  }

  public static PurchaseDto convertToDto(Purchase purchase) {
    return PurchaseDto.builder().purchaseId(purchase.getPurchaseId())
        .purchaseUserId(purchase.getPurchaseUser().getUserId()).deliveringUserId(
            purchase.getDeliveringUser() != null ? purchase.getDeliveringUser().getUserId() : null)
        .shopId(purchase.getShop().getShopId()).menuId(purchase.getMenu().getMenuId())
        .purchaseStatus(purchase.getPurchaseStatus()).isCancelled(purchase.isCancelled()).build();
  }
}