package Not.Delivered.purchase.controller;


import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.domain.PurchaseStatus;
import Not.Delivered.purchase.service.AccessDeniedException;
import Not.Delivered.purchase.service.PurchaseService;
import Not.Delivered.user.domain.UserStatus;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rider/purchases")
public class PurchaseRiderController {

  private final PurchaseService purchaseService;

  // 주문 목록 조회 - RIDER 배달 가능한 주문 조회
  @GetMapping
  public ResponseEntity<ApiResponse<List<Purchase>>> getRiderPurchases(
      @RequestAttribute Long userId,
      @RequestAttribute UserStatus userStatus,
      @RequestParam(required = false) String purchaseStatus) {

    if (userStatus != UserStatus.RIDER) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(ApiResponse.error(HttpStatus.FORBIDDEN, "라이더만 주문 목록을 조회할 수 있습니다."));
    }

    List<Purchase> purchases = purchaseService.getPurchasesForRider(userId, purchaseStatus);

    return ResponseEntity.ok(
        ApiResponse.success(HttpStatus.OK, "주문 목록 조회에 성공했습니다.", purchases)
    );
  }

  // 주문 상세 조회 - RIDER
  @GetMapping("/{purchaseId}")
  public ResponseEntity<ApiResponse<Purchase>> getRiderPurchase(
      @RequestAttribute Long userId,
      @RequestAttribute UserStatus userStatus,
      @PathVariable Long purchaseId) {

    if (userStatus != UserStatus.RIDER) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(ApiResponse.error(HttpStatus.FORBIDDEN, "라이더만 주문을 조회할 수 있습니다."));
    }

    Optional<Purchase> purchaseOptional = purchaseService.getPurchaseForRider(userId, purchaseId);

    if (purchaseOptional.isPresent()) {
      return ResponseEntity.ok(
          ApiResponse.success(HttpStatus.OK, "주문 조회에 성공했습니다.", purchaseOptional.get())
      );
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(ApiResponse.error(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."));
    }
  }

  // 주문 상태 변경 - RIDER (예: COOKED <-> DELIVERING <-> DELIVERED)
  @PatchMapping("/{purchaseId}")
  public ResponseEntity<ApiResponse<Purchase>> updateRiderPurchaseStatus(
      @RequestAttribute Long userId,
      @RequestAttribute UserStatus userStatus,
      @PathVariable Long purchaseId,
      @RequestBody Map<String, String> requestBody) {

    if (userStatus != UserStatus.RIDER) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(ApiResponse.error(HttpStatus.FORBIDDEN, "라이더만 주문 상태를 변경할 수 있습니다."));
    }

    String newStatusStr = requestBody.get("purchaseStatus");
    PurchaseStatus newStatus;
    try {
      newStatus = PurchaseStatus.valueOf(newStatusStr);
    } catch (IllegalArgumentException | NullPointerException e) {
      return ResponseEntity.badRequest()
          .body(ApiResponse.error(HttpStatus.BAD_REQUEST, "유효한 주문 상태를 입력해주세요."));
    }

    try {
      Purchase updatedPurchase = purchaseService.updatePurchaseStatusByRider(userId, purchaseId, newStatus);
      return ResponseEntity.ok(
          ApiResponse.success(HttpStatus.OK, "주문 상태가 성공적으로 변경되었습니다.", updatedPurchase)
      );
    } catch (IllegalStateException | AccessDeniedException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(ApiResponse.error(HttpStatus.BAD_REQUEST, e.getMessage()));
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(ApiResponse.error(HttpStatus.NOT_FOUND, e.getMessage()));
    }
  }
}