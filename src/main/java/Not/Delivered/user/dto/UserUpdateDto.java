package Not.Delivered.user.dto;


import Not.Delivered.common.entity.Address;
import lombok.Getter;
import lombok.Setter;

import Not.Delivered.user.domain.UserStatus;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateDto {

	private String userName;
	private String email;
	private String password;
	private String phoneNumber;
	private Address address;
	private UserStatus userStatus;

}