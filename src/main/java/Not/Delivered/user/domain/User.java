package Not.Delivered.user.domain;

import Not.Delivered.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

  //    // 필요한 경우에만 Setter 메서드 구현
  @Setter
  @Column(name = "user_name", nullable = false)
	private String userName;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(name = "user_status", nullable = false)
	private UserStatus userStatus;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "street")
	private String street;

	@Column(name = "detailed_address1")
	private String detailedAddress1;

	@Column(name = "detailed_address2")
	private String detailedAddress2;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "is_withdrawal")
	private Boolean isWithdrawal;


	// 주소 정보 업데이트 메서드
	public void updateAddress(String city, String state, String street, String detailedAddress1, String detailedAddress2) {
		this.city = city;
		this.state = state;
		this.street = street;
		this.detailedAddress1 = detailedAddress1;
		this.detailedAddress2 = detailedAddress2;
	}

	// equals and hashCode 재정의
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;
		return Objects.equals(userId, user.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}
}

