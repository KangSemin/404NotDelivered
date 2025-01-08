package Not.Delivered.auth.controller;

import Not.Delivered.auth.dto.LoginRequestDto;
import Not.Delivered.auth.dto.SignupRequestDto;
import Not.Delivered.auth.service.AuthService;
import Not.Delivered.auth.service.LogoutService;
import Not.Delivered.common.config.JwtConfig;
import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final JwtConfig jwtConfig;
	private final LogoutService logoutService;

	@PostMapping("/users")
	public ResponseEntity<ApiResponse<Map<String,Object>>> signup(@RequestBody SignupRequestDto signupRequestDto) {
		User user = authService.signUpUser(signupRequestDto);
		String token = jwtConfig.generateToken(user.getUserId(), user.getUserStatus());

		Map<String,Object> response = new HashMap<>();
		response.put("userId",user.getUserId());

		response.put("token",token);

		ApiResponse<Map<String, Object>> success = ApiResponse.success(HttpStatus.CREATED, "회원 가입 성공!",
				response);

		return new ResponseEntity<>(success,HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<Map<String,Object>>> login(@RequestBody LoginRequestDto loginRequestDto) {
		User user = authService.login(loginRequestDto);
		String token = jwtConfig.generateToken(user.getUserId(),user.getUserStatus());

		Map<String,Object> response = new HashMap<>();
		response.put("userId",user.getUserId());
		response.put("userStatus",user.getUserStatus());
		response.put("token",token);

		return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK,"로그인 성공!",response));
	}

	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Map<String,Object>>> logout(@RequestHeader("Authorization") String token) {
		String jwt = token.replace("Bearer ","");
		logoutService.addToBlacklist(jwt);

		Map<String,Object> response = new HashMap<>();
		response.put("token",jwt);

		return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK,"로그아웃 성공!",response));
	}

}
