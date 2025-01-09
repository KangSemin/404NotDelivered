package Not.Delivered.review.service;

import Not.Delivered.comment.domain.Dto.CommentReviewDto;
import Not.Delivered.comment.repository.CommentRepository;
import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.repository.PurchaseRepository;
import Not.Delivered.review.OnlyOneDateException;
import Not.Delivered.review.domain.Dto.ReviewCreateRequestDto;
import Not.Delivered.review.domain.Dto.ReviewDto;
import Not.Delivered.review.domain.Dto.ReviewListDto;
import Not.Delivered.review.domain.Dto.ReviewUpdateRequestDto;
import Not.Delivered.review.domain.Review;
import Not.Delivered.review.repository.ReviewRepository;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.shop.repository.ShopRepository;
import Not.Delivered.user.domain.User;
import Not.Delivered.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final PurchaseRepository purchaseRepository;
  private final UserRepository userRepository;
  private final ShopRepository shopRepository;
  private final CommentRepository commentRepository;


  public ReviewDto createReview(Long userId, ReviewCreateRequestDto requestDto) {
    if (reviewRepository.existsByPurchase_PurchaseId(requestDto.purchaseId())) {
      throw new OnlyOneDateException("One Purchase, One Review");
    }
    Purchase purchase = purchaseRepository.findById(requestDto.purchaseId()).orElseThrow(
        () -> new IllegalArgumentException(
            "Purchase not found with ID:" + requestDto.purchaseId()));

    Review.validReview(userId, purchase);

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found with ID:" + userId));

    Shop shop = shopRepository.findById(purchase.getShop().getShopId()).orElseThrow(
        () -> new IllegalArgumentException(
            "Shop not found with ID:" + purchase.getShop().getShopId()));

    Review review = Review.builder().reviewContent(requestDto.reviewContent())
        .starPoint(requestDto.starPoint()).purchase(purchase).shop(shop).user(user).build();

    reviewRepository.save(review);

    return ReviewDto.convertDto(review);
  }

  public void deleteReview(Long userId, Long reviewId) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new IllegalArgumentException("Review not fount with ID:" + reviewId));

    Review.ownerValidate(review, userId);
    reviewRepository.delete(review);

  }

  public ReviewDto updateReview(Long userId, Long reviewId, ReviewUpdateRequestDto requestDto) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new IllegalArgumentException("Review not fount with ID:" + reviewId));

    Review.ownerValidate(review, userId);

    review.setReview(requestDto);

    reviewRepository.save(review);

    return ReviewDto.convertDto(review);
  }

  public List<ReviewListDto> getShopReview(Long shopId) {

    List<Review> reviewList = reviewRepository.findAllByShopShopIdOrderByCreatedAt(shopId);

    return reviewList.stream().map(
        review -> ReviewListDto.builder()
            .userId(review.getUser().getUserId())
            .createdAt(review.getCreatedAt())
            .updatedAt(review.getLastModifiedAt())
            .starPoint(review.getStarPoint())
            .reviewContent(review.getReviewContent())
            .shopId(review.getShop().getShopId())
            .reviewId(review.getReviewId())
            .purchaseId(review.getPurchase().getPurchaseId())
            .comment(Optional.ofNullable(commentRepository.findByReviewReviewId(review.getReviewId()))
                .map(CommentReviewDto::convertDto)
                .orElse(null))
            .build())
        .toList();
  }

}
