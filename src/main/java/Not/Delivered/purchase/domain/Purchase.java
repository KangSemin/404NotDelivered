package Not.Delivered.purchase.domain;

import Not.Delivered.common.entity.BaseTime;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.user.domain.User;
import Not.Delivered.menu.domain.Menu;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "purchase")
@Getter
@NoArgsConstructor // JPA 연동 위해 필수적
public class Purchase extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "purchase_id")
  private Long purchaseId;

  // 주문한 사용자
  @ManyToOne
  @JoinColumn(name = "purchase_user_id", nullable = false)
  private User purchaseUser;

  // 배달하는 사용자
  @ManyToOne
  @Setter
  @JoinColumn(name = "delivery_user_id")
  private User deliveringUser;

  // 주문한 가게
  @ManyToOne
  @JoinColumn(name = "shop_id", nullable = false)
  private Shop shop;

  // 주문한 메뉴
  @ManyToOne
  @JoinColumn(name = "menu_id", nullable = false)
  private Menu menu;

  // 주문 상태
  @Enumerated(EnumType.STRING)
  @Column(name = "purchase_status", nullable = false)
  private PurchaseStatus purchaseStatus;

  // 주문 취소 여부
  @Column(name = "is_cancelled", nullable = false)
  private boolean isCancelled = false;

  // 주문 상태 변경 메서드
  public void changeStatus(PurchaseStatus newStatus) {
    if (!this.purchaseStatus.canTransitionTo(newStatus)) {
      throw new IllegalStateException("주문 상태를 해당 상태로 전환할 수 없습니다.");
    }
    this.purchaseStatus = newStatus;
  }

  public boolean isOwnedByThisUserId(Long userId) {
    if (this.purchaseUser.getUserId().equals(userId)) {
      return true;
    }
    return false;
  }

  public boolean isShopOwnedByThisUserId(Long userId) {
    if (this.shop.getOwnerUser().getUserId().equals(userId)) {
      return true;
    }
    return false;
  }

  // 주문 취소 메서드
  public void cancel() {
    if (this.purchaseStatus != PurchaseStatus.PENDING) {
      throw new IllegalStateException("대기중인 주문만 취소할 수 있습니다.");
    }
    this.isCancelled = true;
  }


  // 주문 생성 메서드
  @Builder
  public Purchase(User purchaseUser, Shop shop, Menu menu, PurchaseStatus purchaseStatus) {
    this.purchaseUser = purchaseUser;
    this.shop = shop;
    this.menu = menu;
    this.purchaseStatus = purchaseStatus;
  }

  // equals and hashCode 재정의
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Purchase purchase = (Purchase) o;
    return Objects.equals(purchaseId, purchase.purchaseId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(purchaseId);
  }
}