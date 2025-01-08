package Not.Delivered.user.dto;


import Not.Delivered.common.entity.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {

	@NotBlank
	private String userName;

	@NotBlank
	private String email;

	private String phoneNumber;

	private Address address;
}