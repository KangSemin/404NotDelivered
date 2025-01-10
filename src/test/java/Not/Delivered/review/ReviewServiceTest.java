package Not.Delivered.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import Not.Delivered.common.exception.OwnerDataException;
import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.domain.PurchaseStatus;
import Not.Delivered.purchase.repository.PurchaseRepository;
import Not.Delivered.review.domain.Dto.ReviewCreateRequestDto;
import Not.Delivered.review.domain.Dto.ReviewDto;
import Not.Delivered.review.domain.Dto.ReviewUpdateRequestDto;
import Not.Delivered.review.domain.Review;
import Not.Delivered.review.repository.ReviewRepository;
import Not.Delivered.review.service.ReviewService;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.shop.repository.ShopRepository;
import Not.Delivered.user.domain.User;
import Not.Delivered.user.domain.UserStatus;
import Not.Delivered.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

  private static final Logger log = LoggerFactory.getLogger(ReviewServiceTest.class);
  @InjectMocks
  ReviewService reviewService;

  @Mock
  ReviewRepository reviewRepository;

  @Mock
  PurchaseRepository purchaseRepository;

  @Mock
  UserRepository userRepository;

  @Mock
  ShopRepository shopRepository;

  @Mock
  User user;

  @Mock
  Shop shop;

  @Mock
  Purchase purchase;

  @Mock
  Review reviewMock;

  @Test
  void 리뷰저장() {

    //given
    Long userId = 1L;
    Long shopId = 1L;

    ReviewCreateRequestDto requestDto = new ReviewCreateRequestDto(1L, "맛없어요", 1L);

    User normalUser = User.builder().userName("heehee").userStatus(UserStatus.NORMAL_USER)
        .email("heehee@naver.com").phoneNumber("010-1234-5677").userName("헤헤").password("1234")
        .build();

    LocalTime openTime = LocalTime.of(11, 0, 0);
    LocalTime closeTime = LocalTime.of(23, 0, 0);

    Shop shop = Shop.builder().shopName("테스트1가게").minOrderPrice(12000L).openTime(openTime)
        .closeTime(closeTime).isClosing(false).phoneNumber("010-1234-5678").introduce("테스트용 가게입니다.")
        .build();

    Review review = Review.builder().reviewContent("맛없어요").starPoint(1L).user(normalUser)
        .purchase(purchase).shop(shop).build();

    //when
    when(reviewRepository.existsByPurchase_PurchaseId(requestDto.purchaseId())).thenReturn(false);
    when(purchaseRepository.findById(requestDto.purchaseId())).thenReturn(
        Optional.ofNullable(purchase));
    when(purchase.getPurchaseStatus()).thenReturn(PurchaseStatus.DELIVERED);
    when(purchase.getCreatedAt()).thenReturn(LocalDateTime.now());
    when(purchase.getPurchaseUser()).thenReturn(user);
    when(user.getUserId()).thenReturn(userId);
    when(userRepository.findById(userId)).thenReturn(Optional.of(normalUser));
    when(purchase.getShop()).thenReturn(this.shop);
    when(this.shop.getShopId()).thenReturn(shopId);
    when(shopRepository.findById(shopId)).thenReturn(Optional.of(shop));
    when(reviewRepository.save(any(Review.class))).thenReturn(review);

    ReviewDto reviewDto = reviewService.createReview(userId, requestDto);

    //then
    assertThat(reviewDto.getReviewContent()).isEqualTo("맛없어요");
    assertThat(reviewDto.getStarPoint()).isEqualTo(1L);

  }

  @Test
  void 리뷰_수정_실패_본인의_리뷰가_아님() {

    Long userId = 1L;//리뷰의 아이디
    Long wrongUserId = 2L;
    Long reviewId = 1L;

    when(reviewRepository.findById(reviewId)).thenReturn(Optional.ofNullable(reviewMock));
    when(reviewMock.getUser()).thenReturn(user);
    when(user.getUserId()).thenReturn(userId);

    ReviewUpdateRequestDto requestDto = new ReviewUpdateRequestDto(2L, "조금은 맛있네요.");

    assertThrows(OwnerDataException.class, () -> {
      reviewService.updateReview(wrongUserId, reviewId, requestDto);
    });
  }

  @Test
  void 리뷰_생성_실패_주문한지_7일이_지남() {

    ReviewCreateRequestDto requestDto = new ReviewCreateRequestDto(1L, "맛없어요", 1L);

    when(reviewRepository.existsByPurchase_PurchaseId(requestDto.purchaseId())).thenReturn(false);
    when(purchaseRepository.findById(requestDto.purchaseId())).thenReturn(
        Optional.ofNullable(purchase));
    when(purchase.getPurchaseStatus()).thenReturn(PurchaseStatus.DELIVERED);
    when(purchase.getCreatedAt()).thenReturn(LocalDateTime.now().minusDays(20));

    log.info(LocalDateTime.now().toString());

    assertThrows(IllegalArgumentException.class, () -> {
      reviewService.createReview(1L, requestDto);
    });
  }


}
