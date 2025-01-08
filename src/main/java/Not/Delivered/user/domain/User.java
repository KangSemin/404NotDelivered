package Not.Delivered.user.domain;

import Not.Delivered.common.entity.Address;
import Not.Delivered.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.Builder;
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

	@Column(name = "is_withdrawal")
	private Boolean isWithdrawal;

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
	public User(String userName, String email, UserStatus userStatus, String password, String phoneNumber, Address address) {
		this.userName = userName;
		this.email = email;
		this.userStatus = userStatus;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
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

