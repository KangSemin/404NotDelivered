package Not.Delivered.shop.controller;

import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.shop.dto.ShopCreateRequestDto;
import Not.Delivered.shop.dto.ShopCreateResponseDto;
import Not.Delivered.shop.dto.ShopUpdateRequestDto;
import Not.Delivered.shop.dto.ShopUpdateResponseDto;
import Not.Delivered.shop.service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {

  private final ShopService shopService;

  @PostMapping
  public ResponseEntity<ApiResponse<ShopCreateResponseDto>> createShop(
      @RequestAttribute Long userId, @Valid @RequestBody ShopCreateRequestDto dto) {
    ShopCreateResponseDto shopCreateResponseDto = shopService.createShop(userId, dto);

    ApiResponse<ShopCreateResponseDto> response =
        ApiResponse.success(HttpStatus.CREATED, null, shopCreateResponseDto);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PatchMapping("/{shopId}")
  public ResponseEntity<ApiResponse<ShopUpdateResponseDto>> updateShop(
      @RequestAttribute Long userId,
      @PathVariable Long shopId,
      @Valid @RequestBody ShopUpdateRequestDto dto) {
    ShopUpdateResponseDto shopUpdateResponseDto = shopService.updateShop(userId, shopId, dto);

    ApiResponse<ShopUpdateResponseDto> response =
        ApiResponse.success(HttpStatus.OK, null, shopUpdateResponseDto);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
