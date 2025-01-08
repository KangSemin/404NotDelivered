package Not.Delivered.review.domain;

import Not.Delivered.common.entity.BaseTime;
import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.domain.PurchaseStatus;
import Not.Delivered.review.OwnerDataException;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "review")
public class Review extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "review_id", nullable = false)
  private Long reviewId;

  @Column(name = "star_point", nullable = false)
  private Long startPoint;

  @Column(name = "review_content", nullable = false)
  private String reviewContent;

  @OneToOne
  @JoinColumn(name = "purchase_id", nullable = false, unique = true)
  private Purchase purchase;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  //OndeleteOption 고민
  @ManyToOne
  @JoinColumn(name = "shop_id", nullable = false)
  private Shop shop;

  @Builder
  public Review(Long reviewId, Long startPoint, String reviewContent, Purchase purchase, User user,
      Shop shop) {
    this.reviewId = reviewId;
    this.startPoint = startPoint;
    this.reviewContent = reviewContent;
    this.purchase = purchase;
    this.user = user;
    this.shop = shop;
  }

  public static void validReview(Long userId, Purchase purchase)
  {
    if(!purchase.getPurchaseStatus().equals(PurchaseStatus.DELIVERED)){
      throw new IllegalArgumentException("배달 완료 이후에 리뷰 작성이 가능합니다.");
    }
    LocalDateTime localDateTime = purchase.getCreatedAt().plusDays(7);
    if(LocalDateTime.now().isAfter(localDateTime)) {
      throw new IllegalArgumentException("주문 후 7일 이내에 작성해야 합니다.");
    }
    if (!purchase.getPurchaseUser().getUserId().equals(userId)) {
      throw new OwnerDataException("해당 주문의 유저만 리뷰를 작성할 수 있습니다.");
    }

    if (purchase.getPurchaseStatus().equals(PurchaseStatus.DELIVERED)) {
      throw new IllegalArgumentException("배달 완료되지 않은 주문은 리뷰를 남길 수 없습니다.");
    }
  }

  public static void ownerValidate(Review review, Long userId) {
    if (!review.getUser().getUserId().equals(userId)) {
      throw new OwnerDataException("본인의 리뷰만 삭제할 수 있습니다.");
    }
  }
}
