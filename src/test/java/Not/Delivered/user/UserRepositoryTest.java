package Not.Delivered.user;

import static org.assertj.core.api.Assertions.assertThat;

import Not.Delivered.common.entity.Address;
import Not.Delivered.user.domain.User;
import Not.Delivered.user.domain.UserStatus;
import Not.Delivered.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  public void 유저_저장_성공_테스트() {
    //given
    Address userAddress = new Address("서울", "성북구", "장위동", "상세주소1", "상세주소2");
    User normalUser = User.builder().userName("heehee").userStatus(UserStatus.NORMAL_USER)
        .email("heehee@naver.com").phoneNumber("010-1234-5677").userName("헤헤").address(userAddress)
        .password("1234").build();
    //when
    User saveUser = userRepository.save(normalUser);

    //then
    User expectedUser = User.builder().userName("heehee").userStatus(UserStatus.NORMAL_USER)
        .email("heehee@naver.com").phoneNumber("010-1234-5677").userName("헤헤").address(userAddress)
        .password("1234").build();
    assertThat(saveUser).usingRecursiveComparison().ignoringFields("userId")
        .ignoringFields("createdAt").ignoringFields("lastModifiedAt").isEqualTo(expectedUser);
  }

}
