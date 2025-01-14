package Not.Delivered.user.domain;

import Not.Delivered.common.entity.Address;
import Not.Delivered.common.entity.BaseTime;
import Not.Delivered.user.dto.UserUpdateDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor // JPA 연동 위해 필수적
@Table(name = "user")
public class User extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long userId;

// 필요한 경우에만 Setter 메서드 구현
  @Column(name = "user_name", nullable = false)
  private String userName;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_status", nullable = false)
  private UserStatus userStatus;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "phone_number")
  private String phoneNumber;


  @Column(name = "is_withdrawal", nullable = false)
  @Setter
  private Boolean isWithdrawal = false;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "city", column = @Column(name = "city", nullable = false)),
      @AttributeOverride(name = "state", column = @Column(name = "state", nullable = false)),
      @AttributeOverride(name = "street", column = @Column(name = "street", nullable = false)),
      @AttributeOverride(name = "detailAddress1", column = @Column(name = "detail_address1")),
      @AttributeOverride(name = "detailAddress2", column = @Column(name = "detail_address2"))

  })
  private Address address;

  @Builder
  public User(String userName, String email, UserStatus userStatus, String password,
      String phoneNumber, Address address) {
    this.userName = userName;
    this.email = email;
    this.userStatus = userStatus;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.address = address;
  }

  // equals and hashCode 재정의


  @Builder
  public User(Long userId, String userName, String email, UserStatus userStatus, String password,
      String phoneNumber) {
    this.userId = userId;
    this.userName = userName;
    this.email = email;
    this.userStatus = userStatus;
    this.password = password;
    this.phoneNumber = phoneNumber;
  }

  public void update(UserUpdateDto userUpdateDto) {
    if (userUpdateDto.getUserName() != null) {
      this.userName = userUpdateDto.getUserName();
    }
    if (userUpdateDto.getEmail() != null) {
      this.email = userUpdateDto.getEmail();
    }
    if (userUpdateDto.getPassword() != null) {
      this.password = userUpdateDto.getPassword();
    }
    if (userUpdateDto.getPhoneNumber() != null) {
      this.phoneNumber = userUpdateDto.getPhoneNumber();
    }
    if (userUpdateDto.getAddress() != null) {
      this.address = userUpdateDto.getAddress();
    }
    if (userUpdateDto.getUserStatus() != null) {
      this.userStatus = userUpdateDto.getUserStatus();
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;
    return Objects.equals(userId, user.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId);
  }
}

