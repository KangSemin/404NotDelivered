package Not.Delivered.review.domain.Dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
public class ReviewWithCommentDto {

  private Long reviewId;
  private Long userId;
  private LocalDateTime createdAt;
  private LocalDateTime lastModifiedAt;
  private Long starPoint;
  private String reviewContent;
  private Long shopId;
  private Long purchaseId;

  //  private Comment comment;
  private String commentContent;

  @Builder
  public ReviewWithCommentDto(Long reviewId, Long userId, LocalDateTime createdAt,
      LocalDateTime lastModifiedAt, Long starPoint, String reviewContent, Long shopId,
      Long purchaseId, String commentContent) {
    this.reviewId = reviewId;
    this.userId = userId;
    this.createdAt = createdAt;
    this.lastModifiedAt = lastModifiedAt;
    this.starPoint = starPoint;
    this.reviewContent = reviewContent;
    this.shopId = shopId;
    this.purchaseId = purchaseId;
    this.commentContent = commentContent;
  }
}


