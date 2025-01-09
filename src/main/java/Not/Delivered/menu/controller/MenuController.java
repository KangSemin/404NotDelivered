package Not.Delivered.menu.controller;

import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.menu.dto.MenuCreateRequestDto;
import Not.Delivered.menu.dto.MenuCreateResponseDto;
import Not.Delivered.menu.service.MenuService;
import Not.Delivered.user.domain.UserStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {

  private final MenuService menuService;

  @PostMapping
  public ResponseEntity<ApiResponse<MenuCreateResponseDto>> createMenu(
      @RequestAttribute Long userId,
      @RequestAttribute UserStatus userRole,
      @Valid @RequestBody MenuCreateRequestDto dto) {
    MenuCreateResponseDto menuCreateResponseDto = menuService.createMenu(userId, userRole, dto);
    ApiResponse<MenuCreateResponseDto> response =
        ApiResponse.success(HttpStatus.CREATED, null, menuCreateResponseDto);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
