package Not.Delivered.auth.dto;

import Not.Delivered.common.entity.Address;
import Not.Delivered.user.domain.UserStatus;
import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class SignupRequestDto {

	@Email private String email;
	private String password;
	private String username;
	private UserStatus userStatus;
	private Address address;
}
