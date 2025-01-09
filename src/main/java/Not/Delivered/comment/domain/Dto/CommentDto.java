package Not.Delivered.comment.domain.Dto;

import Not.Delivered.comment.domain.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

  private Long commentId;
  private Long reviewId;
  private Long userId;
  private String commentContent;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static CommentDto convertDto(Comment comment) {
    return new CommentDto(
        comment.getCommentId(),
        comment.getReview().getReviewId(),
        comment.getUser().getUserId(),
        comment.getCommentContent(),
        comment.getCreatedAt(),
        comment.getLastModifiedAt()
    );
  }

}
