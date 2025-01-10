package Not.Delivered.review;

import static org.assertj.core.api.Assertions.assertThat;

import Not.Delivered.common.entity.Address;
import Not.Delivered.menu.domain.Menu;
import Not.Delivered.menu.repository.MenuRepository;
import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.domain.PurchaseStatus;
import Not.Delivered.purchase.repository.PurchaseRepository;
import Not.Delivered.review.domain.Review;
import Not.Delivered.review.repository.ReviewRepository;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.shop.repository.ShopRepository;
import Not.Delivered.user.domain.User;
import Not.Delivered.user.domain.UserStatus;
import Not.Delivered.user.repository.UserRepository;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewRepositoryTest {

  @Autowired
  private ReviewRepository reviewRepository;
  @Autowired
  private PurchaseRepository purchaseRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ShopRepository shopRepository;
  @Autowired
  private MenuRepository menuRepository;


  @Test
  void 리뷰_저장_성공_테스트() {
    //given
    Address userAddress = new Address("서울", "성북구", "장위동", "상세주소1", "상세주소2");

    User normalUser = User.builder()
        .userName("heehee")
        .userStatus(UserStatus.NORMAL_USER)
        .email("heehee@naver.com")
        .phoneNumber("010-1234-5677")
        .address(userAddress)
        .userName("헤헤")
        .password("1234")
        .build();

    User ownerUser = User.builder()
        .userName("gege")
        .address(userAddress)
        .userStatus(UserStatus.OWNER)
        .email("gege@naver.com")
        .phoneNumber("010-1234-5678")
        .password("1234")
        .address(userAddress)
        .userName("제제").build();

    User riderUser = User.builder()
        .userName("pepe")
        .userStatus(UserStatus.RIDER)
        .password("1234")
        .email("pepe@naver.com")
        .phoneNumber("010-1234-5679")
        .address(userAddress)
        .userName("페페")
        .build();

    userRepository.saveAndFlush(normalUser);
    userRepository.saveAndFlush(ownerUser);
    userRepository.saveAndFlush(riderUser);

    LocalTime openTime = LocalTime.of(11, 0, 0);
    LocalTime closeTime = LocalTime.of(23, 0, 0);

    Address shopAddress = new Address("시티", "스테이트", "스트릿", "상세주소1", "상세주소2");

    Shop shop = Shop.builder()
        .shopName("테스트1가게")
        .minOrderPrice(12000L).openTime(openTime)
        .closeTime(closeTime)
        .isClosing(false)
        .phoneNumber("010-1234-5678").address(shopAddress)
        .ownerUser(ownerUser)
        .introduce("테스트용 가게입니다.").build();

    shopRepository.save(shop);

    Menu menu1 = Menu.builder()
        .menuName("메뉴1")
        .price(12000)
        .shop(shop)
        .build();

    menuRepository.save(menu1);

    Purchase purchase = Purchase.builder()
        .purchaseStatus(PurchaseStatus.DELIVERED)
        .purchaseUser(normalUser)
        .menu(menu1)
        .shop(shop)
        .build();

    purchaseRepository.save(purchase);

    Review review = Review.builder().reviewContent("맛잇어요").starPoint(5L).user(normalUser).purchase(purchase).shop(shop).build();

    //when
    Review saveReview = reviewRepository.save(review);

    //then
    Review expectedReview = Review.builder().reviewContent("맛잇어요").starPoint(5L).user(normalUser).build();
    assertThat(saveReview)
        .usingRecursiveComparison()
        .ignoringFields("reviewId")
        .ignoringFields("purchase")
        .ignoringFields("shop")
        .ignoringFields("createdAt")
        .ignoringFields("lastModifiedAt")
        .isEqualTo(expectedReview);

  }

}
