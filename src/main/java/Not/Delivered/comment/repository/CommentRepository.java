package Not.Delivered.comment.repository;

import Not.Delivered.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  boolean existsByReview_ReviewId(Long reviewId);

  Comment findByReviewReviewId(Long reviewId);
}
