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
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  // 회원정보수정
  @PatchMapping
  public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(@RequestAttribute Long userId,
      @Valid @RequestBody UserUpdateDto userUpdateDto) {

    UserResponseDto userResponseDto = userService.updateUser(userId, userUpdateDto);

    ApiResponse<UserResponseDto> apiResponse = ApiResponse.success(HttpStatus.OK, "회원정보가 성공적으로 수정되었습니다.", userResponseDto);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  // 회원탈퇴
  @DeleteMapping
  public ResponseEntity<ApiResponse<Void>> deleteUser(@RequestAttribute Long userId) {
    userService.deleteUser(userId);
    ApiResponse<Void> apiResponse = ApiResponse.success(HttpStatus.OK, "회원 탈퇴가 성공적으로 처리되었습니다.", null);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  // 회원정보조회
  @GetMapping
  public ResponseEntity<ApiResponse<UserResponseDto>> getUser(@RequestAttribute Long userId) {
    UserResponseDto userResponseDto = userService.getUserById(userId);
    ApiResponse<UserResponseDto> apiResponse = ApiResponse.success(HttpStatus.OK, "회원 정보 조회 성공", userResponseDto);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }
}