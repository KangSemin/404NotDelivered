//package Not.Delivered.review;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import Not.Delivered.review.domain.Review;
//import Not.Delivered.review.repository.ReviewRepository;
//import Not.Delivered.user.domain.User;
//import Not.Delivered.user.domain.UserStatus;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//@DataJpaTest
//public class ReviewRepositoryTest {
//
//  @Autowired
//  private ReviewRepository reviewRepository;
//
//  @Test
//  void 리뷰_저장_성공_테스트() {
//    //given
//    User normalUser = User.builder().userName("heehee").userStatus(UserStatus.NORMAL_USER)
//        .email("heehee@naver.com").phoneNumber("010-1234-5677").userName("헤헤").build();
//    User ownerUser = User.builder().userName("gege").userStatus(UserStatus.OWNER)
//        .email("gege@naver.com").phoneNumber("010-1234-5678").userName("제제").build();
//    User riderUser = User.builder().userName("pepe").userStatus(UserStatus.RIDER)
//        .email("pepe@naver.com").phoneNumber("010-1234-5679").userName("페페").build();
//
//    //샵
//    //주문
//
//    Review review = Review.builder().reviewContent("맛잇어요").startPoint(5L).user(normalUser).build();
//
//    //when
//    Review saveReview = reviewRepository.save(review);
//
//    //then
//    Review expectedReview = Review.builder().reviewContent("맛잇어요").startPoint(5L).user(normalUser).build();
//    assertThat(saveReview)
//        .usingRecursiveComparison()
//        .ignoringFields("review_id")
//        .ignoringFields("purchase_id")
//        .ignoringFields("shop_id")
//        .isEqualTo(expectedReview);
//
//  }
//
//}
