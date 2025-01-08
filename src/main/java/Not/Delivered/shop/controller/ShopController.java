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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {

  private final ShopService shopService;

  @PostMapping
  public ResponseEntity<ApiResponse<ShopCreateResponseDto>> createShop(@Valid @RequestBody
  ShopCreateRequestDto dto) {
    // TODO 로그인 정보에서 사용자 ID 얻어와서 사용하기
    ShopCreateResponseDto shopCreateResponseDto = shopService.createShop(dto);

    ApiResponse<ShopCreateResponseDto> response = ApiResponse.success(HttpStatus.CREATED, null,
        shopCreateResponseDto);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
