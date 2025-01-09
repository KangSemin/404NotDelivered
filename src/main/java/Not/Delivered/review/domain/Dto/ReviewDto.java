package Not.Delivered.review.domain.Dto;

import Not.Delivered.review.domain.Review;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

  private Long reviewId;
  private Long purchaseId;
  private Long userId;
  private Long shopId;
  private Long starPoint;
  private String reviewContent;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static ReviewDto convertDto(Review review) {
    return new ReviewDto(
        review.getReviewId(),
        review.getPurchase().getPurchaseId(),
        review.getUser().getUserId(),
        review.getShop().getShopId(),
        review.getStarPoint(),
        review.getReviewContent(),
        review.getCreatedAt(),
        review.getLastModifiedAt()
    );
  }


}
