package Not.Delivered.purchase.controller;

import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.dto.PurchaseCreateDto;
import Not.Delivered.purchase.dto.PurchaseUpdateDto;
import Not.Delivered.purchase.service.PurchaseService;
import Not.Delivered.user.domain.UserStatus;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/purchases")
public class TempPurchaseController {

  private final PurchaseService purchaseService;

  // 주문 생성 - NORMAL_USER
  @PostMapping
  public ResponseEntity<ApiResponse<Purchase>> createPurchase(
      @RequestAttribute Long userId,
      @Valid @RequestBody PurchaseCreateDto purchaseCreateDto) {

    Purchase newPurchase = purchaseService.createPurchase(userId, purchaseCreateDto);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(HttpStatus.CREATED, "주문이 성공적으로 생성되었습니다.", newPurchase));
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

  // 주문 목록 조회 - 사용자별 접근 권한에 따라
  @GetMapping
  public ResponseEntity<ApiResponse<List<Purchase>>> getPurchases(
      @RequestAttribute Long userId,
      @RequestAttribute UserStatus userStatus,
      @RequestParam(required = false) String purchaseStatus) {

    List<Purchase> purchases = purchaseService.getPurchases(userId, userStatus, purchaseStatus);

    return ResponseEntity.ok(
        ApiResponse.success(HttpStatus.OK, "주문 목록 조회에 성공했습니다.", purchases)
    );
  }

  // 주문 상세 조회 - 사용자별 접근 권한에 따라
  @GetMapping("/{purchaseId}")
  public ResponseEntity<ApiResponse<Purchase>> getPurchase(
      @RequestAttribute Long userId,
      @RequestAttribute UserStatus userStatus,
      @PathVariable Long purchaseId) {

    Optional<Purchase> purchaseOptional = purchaseService.getPurchase(userId, userStatus, purchaseId);

    if (purchaseOptional.isPresent()) {
      return ResponseEntity.ok(
          ApiResponse.success(HttpStatus.OK, "주문 조회에 성공했습니다.", purchaseOptional.get())
      );
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(ApiResponse.error(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."));
    }
  }

  // 주문 상태 변경 - OWNER 또는 RIDER
  @PatchMapping("/{purchaseId}")
  public ResponseEntity<ApiResponse<Purchase>> updatePurchaseStatus(
      @RequestAttribute Long userId,
      @RequestAttribute UserStatus userStatus,
      @PathVariable Long purchaseId,
      @Valid @RequestBody PurchaseUpdateDto purchaseUpdateDto) {

    Purchase updatedPurchase = purchaseService.updatePurchaseStatus(userId, userStatus, purchaseId, purchaseUpdateDto);

    return ResponseEntity.ok(
        ApiResponse.success(HttpStatus.OK, "주문 상태가 성공적으로 변경되었습니다.", updatedPurchase)
    );
  }

}