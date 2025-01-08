package Not.Delivered.review.service;

import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.repository.PurchaseRepository;
import Not.Delivered.review.domain.Dto.ReviewCreateRequestDto;
import Not.Delivered.review.domain.Dto.ReviewDto;
import Not.Delivered.review.domain.Review;
import Not.Delivered.review.repository.ReviewRepository;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.shop.repository.ShopRepository;
import Not.Delivered.user.domain.User;
import Not.Delivered.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final PurchaseRepository purchaseRepository;
  private final UserRepository userRepository;
  private final ShopRepository shopRepository;


  public ReviewDto createReview(Long userId, ReviewCreateRequestDto requestDto) {
    Purchase purchase = purchaseRepository.findById(requestDto.purchaseId()).orElseThrow(
        () -> new IllegalArgumentException("Purchase not found with ID:" + requestDto.purchaseId()));

    // 주문한유저와 접속한 유저가 동일인이 아닐 경우 예외처리, 엔티티에 위임
    if (!purchase.getPurchaseUser().getUserId().equals(userId)) {
      //accessDenied Exception
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
}
