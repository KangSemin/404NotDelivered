package Not.Delivered.purchase.controller;

import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.domain.PurchaseStatus;
import Not.Delivered.purchase.dto.PurchaseCreateDto;
import Not.Delivered.purchase.dto.PurchaseDto;
import Not.Delivered.purchase.service.PurchaseService;
import Not.Delivered.user.domain.UserStatus;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/normalUser/purchases")
public class PurchaseNormalUserController {

  private final PurchaseService purchaseService;

  // 주문 생성 - NORMAL_USER
  @PostMapping
  public ResponseEntity<ApiResponse<PurchaseDto>> createPurchase(
      @RequestAttribute Long userId,
      @Valid @RequestBody PurchaseCreateDto purchaseCreateDto) {

    log.info("createPurchase logic start: ");

    PurchaseDto newPurchaseDto = purchaseService.createPurchase(userId, purchaseCreateDto);

    log.info("purchased created: ");
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(HttpStatus.CREATED, "주문이 성공적으로 생성되었습니다.", newPurchaseDto));
  }

  // 주문 취소 - NORMAL_USER (PENDING 상태에서만)
  @DeleteMapping("/{purchaseId}")
  public ResponseEntity<ApiResponse<Void>> cancelPurchase(
      @RequestAttribute Long userId,
      @PathVariable Long purchaseId) {


    purchaseService.cancelPurchase(userId, purchaseId);

    return ResponseEntity.ok(
        ApiResponse.success(HttpStatus.OK, "주문이 성공적으로 취소되었습니다.", null)
    );
  }

  // 주문 목록 조회 - NORMAL_USER 본인의 주문만 조회
  @GetMapping
  public ResponseEntity<ApiResponse<List<PurchaseDto>>> getUserPurchases(
      @RequestAttribute Long userId,
      @RequestParam(required = false) PurchaseStatus purchaseStatus) {

    List<PurchaseDto> purchases = purchaseService.getPurchaseListForUser(userId, purchaseStatus);

    return ResponseEntity.ok(
        ApiResponse.success(HttpStatus.OK, "주문 목록 조회에 성공했습니다.", purchases)
    );
  }

  // 주문 상세 조회 - NORMAL_USER 본인의 주문만 조회
  @GetMapping("/{purchaseId}")
  public ResponseEntity<ApiResponse<PurchaseDto>> getUserPurchase(
      @RequestAttribute Long userId,
      @PathVariable Long purchaseId) {

    PurchaseDto purchase = purchaseService.getPurchaseForUser(userId, purchaseId);

    return ResponseEntity.ok(
        ApiResponse.success(HttpStatus.OK, "주문 조회에 성공했습니다.", purchase)
    );
  }
}