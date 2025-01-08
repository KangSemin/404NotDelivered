package Not.Delivered.auth.controller;

import Not.Delivered.auth.dto.SignupRequestDto;
import Not.Delivered.auth.service.AuthService;
import Not.Delivered.common.config.JwtConfig;
import Not.Delivered.common.dto.ApiResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final JwtConfig jwtConfig;

	@PostMapping("/users")
	public ResponseEntity<ApiResponse<Map<String,Object>>> signup(@RequestBody SignupRequestDto signupRequestDto) {
		Long userId = authService.signUpUser(signupRequestDto);
		String token = jwtConfig.generateToken(userId);

		Map<String,Object> response = new HashMap<>();
		response.put("userId",userId);
		response.put("token",token);

		return ResponseEntity.ok(ApiResponse.success(HttpStatus.CREATED,"회원 가입 성공!",response));
	}


}
