package Not.Delivered.review.domain;

import Not.Delivered.common.entity.BaseTime;
import Not.Delivered.common.exception.OwnerDataException;
import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.domain.PurchaseStatus;
import Not.Delivered.review.domain.Dto.ReviewUpdateRequestDto;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.user.domain.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Optional;
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
  private Long starPoint;

  @Column(name = "review_content", nullable = false)
  private String reviewContent;

  @OneToOne
  @JoinColumn(name = "purchase_id", nullable = false, unique = true)
  private Purchase purchase;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "shop_id", nullable = false)
  private Shop shop;

  @Builder
  public Review(Long reviewId, Long starPoint, String reviewContent, Purchase purchase, User user,
      Shop shop) {
    this.reviewId = reviewId;
    this.starPoint = starPoint;
    this.reviewContent = reviewContent;
    this.purchase = purchase;
    this.user = user;
    this.shop = shop;
  }

  public static void validReview(Long userId, Purchase purchase) {
    if (!purchase.getPurchaseStatus().equals(PurchaseStatus.DELIVERED)) {
      throw new IllegalArgumentException("배달 완료 이후에 리뷰 작성이 가능합니다.");
    }
    LocalDateTime localDateTime = purchase.getCreatedAt().plusDays(7);
    if (LocalDateTime.now().isAfter(localDateTime)) {
      throw new IllegalArgumentException("주문 후 7일 이내에 작성해야 합니다.");
    }
    if (!purchase.getPurchaseUser().getUserId().equals(userId)) {
      throw new OwnerDataException("해당 주문의 유저만 리뷰를 작성할 수 있습니다.");
    }
  }

  public static void ownerValidate(Review review, Long userId) {
    if (!review.getUser().getUserId().equals(userId)) {
      throw new OwnerDataException("본인의 리뷰만 삭제할 수 있습니다.");
    }
  }

  public void setReview(ReviewUpdateRequestDto requestDto) {
    if (requestDto.reviewContent() != null) {
      this.reviewContent = requestDto.reviewContent();
    }

    if (requestDto.starPoint() != null) {
      this.starPoint = requestDto.starPoint();
    }
  }

  public static Shop shopIsNotClosingValidate(Optional<Shop> shop, Long shopId) {
    if(shop.isEmpty()) {
      throw new EntityNotFoundException("Shop not found with ID:" + shopId);
    }
    return shop.get();
  }
}
