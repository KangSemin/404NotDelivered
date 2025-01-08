package Not.Delivered.comment.service;

import Not.Delivered.comment.domain.Dto.CommentCreateRequestDto;
import Not.Delivered.comment.domain.Dto.CommentDto;
import Not.Delivered.comment.repository.CommentRepository;
import Not.Delivered.purchase.repository.PurchaseRepository;
import Not.Delivered.review.domain.Review;
import Not.Delivered.review.repository.ReviewRepository;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.shop.repository.ShopRepository;
import Not.Delivered.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

  private final CommentRepository commentRepository;
  private final PurchaseRepository purchaseRepository;
  private final UserRepository userRepository;
  private final ReviewRepository reviewRepository;
  private final ShopRepository shopRepository;

  public CommentDto createComment(Long userId, Long reviewId, CommentCreateRequestDto requestDto) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new IllegalArgumentException("Review not found with ID:" + reviewId));
    Shop shop = shopRepository.findById(review.getShop().getShopId())
        .orElseThrow(() -> new IllegalArgumentException("Review not exist Shop."));

    //TODO 가게의 주인만 달 수 있게 변경해야해서 기다렸다가 수정 예정


    return null;
  }
}
