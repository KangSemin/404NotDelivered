package Not.Delivered.review.service;

import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.domain.PurchaseStatus;
import Not.Delivered.purchase.repository.PurchaseRepository;
import Not.Delivered.review.domain.Dto.ReviewCreateRequestDto;
import Not.Delivered.review.domain.Dto.ReviewDto;
import Not.Delivered.review.domain.Review;
import Not.Delivered.review.repository.ReviewRepository;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.shop.repository.ShopRepository;
import Not.Delivered.user.domain.User;
import Not.Delivered.user.repository.UserRepository;
import java.nio.file.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final PurchaseRepository purchaseRepository;
  private final UserRepository userRepository;
  private final ShopRepository shopRepository;


  public ReviewDto createReview(Long userId, ReviewCreateRequestDto requestDto)
      throws AccessDeniedException {
    Purchase purchase = purchaseRepository.findById(requestDto.purchaseId()).orElseThrow(
        () -> new IllegalArgumentException(
            "Purchase not found with ID:" + requestDto.purchaseId()));

    // 주문한유저와 접속한 유저가 동일인이 아닐 경우 예외처리, 엔티티에 위임
    if (!purchase.getPurchaseUser().getUserId().equals(userId)) {
      throw new AccessDeniedException("해당 주문의 유저만 리뷰를 작성할 수 있습니다.");
    }

    // 주문 상태가 '배달완료'가 아닐때 예외처리
    if (purchase.getPurchaseStatus().equals(PurchaseStatus.DELIVERED)) {
      throw new AccessDeniedException("배달 완료되지 않은 주문은 리뷰를 남길 수 없습니다.");
    }

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found with ID:" + userId));

    Shop shop = shopRepository.findById(purchase.getShop().getShopId()).orElseThrow(
        () -> new IllegalArgumentException(
            "Shop not found with ID:" + purchase.getShop().getShopId()));

    Review review = Review.builder().reviewContent(requestDto.reviewContent())
        .startPoint(requestDto.starPoint()).purchase(purchase).shop(shop).user(user).build();

    reviewRepository.save(review);

    return ReviewDto.convertDto(review);
  }

  public void deleteReview(Long userId, Long reviewId) throws AccessDeniedException {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new IllegalArgumentException("Review not fount with ID:" + reviewId));

    //본인의 리뷰가 아니면 삭제할 수 없음
    if (!review.getUser().getUserId().equals(userId)) {
      throw new AccessDeniedException("본인의 리뷰만 삭제할 수 있습니다.");
    }

    reviewRepository.delete(review);

  }
}
