package Not.Delivered.shop.repository;

import static org.assertj.core.api.Assertions.assertThat;

import Not.Delivered.common.entity.Address;
import Not.Delivered.shop.domain.Shop;
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
public class ShopRepositoryTest {

  @Autowired
  ShopRepository shopRepository;

  @Autowired
  UserRepository userRepository;

  @Test
  void 가게_생성_성공_테스트() {
    // given
    User user =
        User.builder()
            .userName("테스트 사용자1")
            .email("test1@test.com")
            .userStatus(UserStatus.OWNER)
            .password("password")
            .address(new Address("테스트시", "테스트구", "테스트동", "테스트 아파트", "1동 1호"))
            .phoneNumber("1234-5678")
            .build();

    User savedUser = userRepository.save(user);

    Shop shop =
        Shop.builder()
            .ownerUser(savedUser)
            .shopName("테스트 가게1")
            .introduce("테스트 소개")
            .address(new Address("테스트시", "테스트구", "테스트동", "테스트 아파트", "1동 1호"))
            .phoneNumber("1234-5678")
            .openTime(LocalTime.of(10, 0))
            .closeTime(LocalTime.of(20, 0))
            .minOrderPrice(3000L)
            .build();

    // when
    Shop actualResult = shopRepository.save(shop);

    // then
    Shop expectedShop =
        Shop.builder()
            .ownerUser(user)
            .shopName("테스트 가게1")
            .introduce("테스트 소개")
            .address(new Address("테스트시", "테스트구", "테스트동", "테스트 아파트", "1동 1호"))
            .phoneNumber("1234-5678")
            .openTime(LocalTime.of(10, 0))
            .closeTime(LocalTime.of(20, 0))
            .minOrderPrice(3000L)
            .build();

    assertThat(actualResult)
        .usingRecursiveComparison()
        .ignoringFields("shopId")
        .isEqualTo(expectedShop);
  }
}
