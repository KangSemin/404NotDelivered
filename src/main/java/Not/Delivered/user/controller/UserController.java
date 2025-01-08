package Not.Delivered.user.controller;

import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.user.domain.User;
import Not.Delivered.user.dto.UserUpdateDto;
import Not.Delivered.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")  // 기본 URL 경로
public class UserController {

	private final UserService userService;

	// 회원정보수정
	@PatchMapping
	public ResponseEntity<ApiResponse<User>> updateUser(@RequestAttribute Long userId,
														@Valid @RequestBody UserUpdateDto userUpdateDto) {
		// 에러 처리 로직
		try {
			User updatedUser = userService.updateUser(userId, userUpdateDto);
			return ResponseEntity.ok(
					ApiResponse.success(HttpStatus.OK, "회원정보가 성공적으로 수정되었습니다.", updatedUser)
			);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "회원정보 수정 중 오류가 발생했습니다."));
		}
	}

	// 내 정보 조회
	@GetMapping
	public ResponseEntity<ApiResponse<User>> getMyInfo(@RequestAttribute Long userId) {
		Optional<User> userOptional = userService.getUserById(userId);

		if (userOptional.isPresent()) {
			return ResponseEntity.ok(
					ApiResponse.success(HttpStatus.OK, "회원 정보 조회에 성공했습니다.", userOptional.get())
			);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(ApiResponse.error(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
		}
	}
}