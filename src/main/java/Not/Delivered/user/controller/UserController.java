package Not.Delivered.user.controller;

import Not.Delivered.user.domain.User;
import Not.Delivered.user.dto.UserUpdateDto;
import Not.Delivered.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")  // 기본 URL 경로
public class UserController {

	private final UserService userService;


	// 회원정보수정
	@PatchMapping
	public ResponseEntity<?> updateUser(@RequestAttribute Long userId,
	                                    @Valid @RequestBody UserUpdateDto userUpdateDto) {
//		에러 처리 로직

		User updatedUser = userService.updateUser(userId, userUpdateDto);
		return ResponseEntity.ok(updatedUser);
	}

	// 내 정보 조회
	@GetMapping
	public ResponseEntity<User> getMyInfo(@RequestAttribute Long userId) {
		Optional<User> userOptional = userService.getUserById(userId);
		return userOptional.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}