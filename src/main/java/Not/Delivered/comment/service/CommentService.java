package Not.Delivered.comment.service;

import Not.Delivered.comment.domain.Comment;
import Not.Delivered.comment.domain.Dto.CommentCreateRequestDto;
import Not.Delivered.comment.domain.Dto.CommentDto;
import Not.Delivered.comment.domain.Dto.CommentUpdateRequestDto;
import Not.Delivered.comment.repository.CommentRepository;
import Not.Delivered.common.exception.OnlyOneDataException;
import Not.Delivered.review.domain.Review;
import Not.Delivered.review.repository.ReviewRepository;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.shop.repository.ShopRepository;
import Not.Delivered.user.domain.User;
import Not.Delivered.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final ReviewRepository reviewRepository;
  private final ShopRepository shopRepository;

  public CommentDto createComment(Long userId, Long reviewId, CommentCreateRequestDto requestDto) {

    if (commentRepository.existsByReview_ReviewId(reviewId)) {
      throw new OnlyOneDataException("One Review, One Comment");
    }

    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new IllegalArgumentException("Review not found with ID:" + reviewId));

    Optional<Shop> optionalShop = shopRepository.findByShopIdAndIsClosing(
        review.getShop().getShopId());
    Shop shop = Review.shopIsNotClosingValidate(optionalShop, review.getShop().getShopId());

    Comment.shopOwnerValidate(shop, userId);

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not fount with ID:" + userId));

    Comment comment = Comment.builder().commentContent(requestDto.commentContent()).user(user)
        .review(review).build();

    commentRepository.save(comment);

    return CommentDto.convertDto(comment);
  }

  public void deleteComment(Long userId, Long reviewId, Long commentId) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID:" + commentId));
    Comment.commentReviewAndUserValidate(comment, reviewId, userId);
    commentRepository.delete(comment);
  }

  public CommentDto updateComment(Long userId, Long reviewId, Long commentId,
      CommentUpdateRequestDto requestDto) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID:" + commentId));

    if (shopRepository.findByShopIdAndIsClosing(comment.getReview().getShop().getShopId())
        .isEmpty()) {
      throw new IllegalArgumentException(
          "Shop not found with ID:" + comment.getReview().getShop().getShopId());
    }

    Comment.commentReviewAndUserValidate(comment, reviewId, userId);
    comment.updateCommentContent(requestDto.commentContent());
    commentRepository.save(comment);
    return CommentDto.convertDto(comment);
  }
}
