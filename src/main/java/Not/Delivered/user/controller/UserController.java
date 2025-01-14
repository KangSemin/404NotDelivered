package Not.Delivered.user.controller;

import Not.Delivered.auth.service.LogoutService;
import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.user.dto.UserResponseDto;
import Not.Delivered.user.dto.UserUpdateDto;
import Not.Delivered.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private final LogoutService logoutService;

  // 회원정보수정
  @PatchMapping
  public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(@RequestAttribute Long userId,
      @Valid @RequestBody UserUpdateDto userUpdateDto) {

    UserResponseDto userResponseDto = userService.updateUser(userId, userUpdateDto);

    ApiResponse<UserResponseDto> apiResponse = ApiResponse.success(HttpStatus.OK,
        "회원정보가 성공적으로 수정되었습니다.", userResponseDto);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  // 회원탈퇴
  @DeleteMapping
  public ResponseEntity<ApiResponse<Void>> deleteUser(@RequestAttribute Long userId,
      @RequestHeader("Authorization") String token) {
    String jwt = token.replace("Bearer ","");
    logoutService.addToBlacklist(jwt);
    userService.deleteUser(userId);
    ApiResponse<Void> apiResponse = ApiResponse.success(HttpStatus.OK, "회원 탈퇴가 성공적으로 처리되었습니다.",
        null);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  // 회원정보조회
  @GetMapping
  public ResponseEntity<ApiResponse<UserResponseDto>> getUser(@RequestAttribute Long userId) {
    UserResponseDto userResponseDto = userService.getUserById(userId);
    ApiResponse<UserResponseDto> apiResponse = ApiResponse.success(HttpStatus.OK, "회원 정보 조회 성공",
        userResponseDto);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }
}