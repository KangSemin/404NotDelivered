package Not.Delivered.comment.domain.Dto;

import Not.Delivered.comment.domain.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CommentReviewDto {

  private Long reviewId;
  private Long commentId;
  private Long userId;
  private String commentContent;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static CommentReviewDto convertDto(Comment comment) {
    return new CommentReviewDto(comment.getReview().getReviewId(), comment.getCommentId(),
        comment.getUser().getUserId(), comment.getCommentContent(), comment.getCreatedAt(),
        comment.getLastModifiedAt());
  }
}
