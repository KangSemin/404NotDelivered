package Not.Delivered.shop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import Not.Delivered.common.entity.Address;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.shop.dto.ShopCreateRequestDto;
import Not.Delivered.shop.dto.ShopCreateResponseDto;
import Not.Delivered.shop.repository.ShopRepository;
import Not.Delivered.user.domain.User;
import Not.Delivered.user.domain.UserStatus;
import Not.Delivered.user.repository.UserRepository;
import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ShopServiceTest {

  private final int ONE_TIME = 1;

  @InjectMocks
  ShopService shopService;

  @Mock
  UserRepository userRepository;

  @Mock
  ShopRepository shopRepository;

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

    Shop shop =
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

    ShopCreateRequestDto shopCreateRequestDto =
        new ShopCreateRequestDto(
            "테스트 가게1",
            "테스트 소개",
            new Address("테스트시", "테스트구", "테스트동", "테스트 아파트", "1동 1호"),
            "1234-5678",
            LocalTime.of(10, 0),
            LocalTime.of(20, 0),
            3000L);

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(shopRepository.save(any(Shop.class))).thenReturn(shop);

    // when
    ShopCreateResponseDto actualResult = shopService.createShop(1L,
        shopCreateRequestDto);

    // then
    assertThat(actualResult.getShopName()).isEqualTo("테스트 가게1");
    assertThat(actualResult.getIntroduce()).isEqualTo("테스트 소개");
    assertThat(actualResult.getAddress().getCity()).isEqualTo("테스트시");
    assertThat(actualResult.getAddress().getState()).isEqualTo("테스트구");
    assertThat(actualResult.getAddress().getStreet()).isEqualTo("테스트동");
    assertThat(actualResult.getAddress().getDetailAddress1()).isEqualTo("테스트 아파트");
    assertThat(actualResult.getAddress().getDetailAddress2()).isEqualTo("1동 1호");
    assertThat(actualResult.getPhoneNumber()).isEqualTo("1234-5678");
    assertThat(actualResult.getOpenTime()).isEqualTo(LocalTime.of(10, 0));
    assertThat(actualResult.getCloseTime()).isEqualTo(LocalTime.of(20, 0));
    assertThat(actualResult.getMinOrderPrice()).isEqualTo(3000L);

    verify(shopRepository, times(ONE_TIME)).save(any(Shop.class));
  }
}
