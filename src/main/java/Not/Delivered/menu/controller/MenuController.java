package Not.Delivered.menu.controller;

import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.menu.dto.MenuCreateRequestDto;
import Not.Delivered.menu.dto.MenuCreateResponseDto;
import Not.Delivered.menu.dto.MenuUpdateRequestDto;
import Not.Delivered.menu.dto.MenuUpdateResponseDto;
import Not.Delivered.menu.service.MenuService;
import Not.Delivered.user.domain.UserStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
      @RequestAttribute UserStatus userStatus,
      @Valid @RequestBody MenuCreateRequestDto dto) {
    MenuCreateResponseDto menuCreateResponseDto = menuService.createMenu(userId, userStatus, dto);
    ApiResponse<MenuCreateResponseDto> response =
        ApiResponse.success(HttpStatus.CREATED, null, menuCreateResponseDto);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PatchMapping("/{menuId}")
  public ResponseEntity<ApiResponse<MenuUpdateResponseDto>> updateMenu(
      @RequestAttribute Long userId,
      @RequestAttribute UserStatus userStatus,
      @PathVariable Long menuId,
      @Valid @RequestBody MenuUpdateRequestDto dto) {
    MenuUpdateResponseDto menuUpdateResponseDto =
        menuService.updateMenu(userId, userStatus, menuId, dto);
    ApiResponse<MenuUpdateResponseDto> response =
        ApiResponse.success(HttpStatus.OK, null, menuUpdateResponseDto);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{shopId}/{menuId}")
  public ResponseEntity<ApiResponse<Void>> deleteMenu(
      @RequestAttribute Long userId,
      @RequestAttribute UserStatus userStatus,
      @PathVariable Long shopId,
      @PathVariable Long menuId) {
    menuService.deleteMenu(userId, userStatus, shopId, menuId);

    ApiResponse<Void> response = ApiResponse.success(HttpStatus.OK, "해당 메뉴의 판매가 중지되었습니다.", null);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
