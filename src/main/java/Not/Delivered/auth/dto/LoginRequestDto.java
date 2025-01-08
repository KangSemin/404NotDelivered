package Not.Delivered.auth.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class LoginRequestDto {

  @Email
  private String email;
  private String password;
}
