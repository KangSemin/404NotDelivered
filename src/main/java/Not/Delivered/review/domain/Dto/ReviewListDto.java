package Not.Delivered.review.domain.Dto;

import Not.Delivered.comment.domain.Dto.CommentReviewDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewListDto {

  private Long reviewId;
  private Long purchaseId;
  private Long userId;
  private Long shopId;
  private Long starPoint;
  private String reviewContent;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private CommentReviewDto comment;

  @Builder
  public ReviewListDto(Long reviewId, Long purchaseId, Long userId, Long shopId, Long starPoint,
      String reviewContent, LocalDateTime createdAt, LocalDateTime updatedAt,
      CommentReviewDto comment) {
    this.reviewId = reviewId;
    this.purchaseId = purchaseId;
    this.userId = userId;
    this.shopId = shopId;
    this.starPoint = starPoint;
    this.reviewContent = reviewContent;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.comment = comment;
  }
}
