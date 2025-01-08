package Not.Delivered.shop.controller;

import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.shop.dto.ShopCreateRequestDto;
import Not.Delivered.shop.dto.ShopCreateResponseDto;
import Not.Delivered.shop.service.ShopService;
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
}
