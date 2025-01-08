package Not.Delivered.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class UserUpdateDto {

	private String userName;

	@Email(message = "유효한 이메일 주소여야 합니다.")
	private String email;

	private String password;

}