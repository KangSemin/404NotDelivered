package Not.Delivered.user.controller;

import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.user.dto.UserResponseDto;
import Not.Delivered.user.dto.UserUpdateDto;
import Not.Delivered.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")  // 기본 URL 경로
public class UserController {

  private final UserService userService;

  // 회원정보수정
  @PatchMapping
  public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(@RequestAttribute Long userId,
      @Valid @RequestBody UserUpdateDto userUpdateDto) {

    UserResponseDto userResponseDto = userService.updateUser(userId, userUpdateDto);

    return ResponseEntity.ok(
        ApiResponse.success(HttpStatus.OK, "회원정보가 성공적으로 수정되었습니다.", userResponseDto)
    );
  }

  // 내 정보 조회
  @GetMapping
  public ResponseEntity<ApiResponse<UserResponseDto>> getMyInfo(@RequestAttribute Long userId) {
    UserResponseDto userResponseDto = userService.getUserById(userId);

    return ResponseEntity.ok(
        ApiResponse.success(HttpStatus.OK, "회원 정보 조회에 성공했습니다.", userResponseDto));
  }
}