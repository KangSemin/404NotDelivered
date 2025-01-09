package Not.Delivered.purchase.controller;


import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.domain.PurchaseStatus;
import Not.Delivered.purchase.service.PurchaseService;
import Not.Delivered.user.domain.UserStatus;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    return purchaseService.getPurchaseForRider(userId, purchaseId)
        .map(purchase -> ResponseEntity.ok(
            ApiResponse.success(HttpStatus.OK, "주문 조회에 성공했습니다.", purchase)
        ))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.error(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."))
        );
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

    Purchase updatedPurchase = purchaseService.updatePurchaseStatusByRider(userId, purchaseId, newStatus);

    return ResponseEntity.ok(
        ApiResponse.success(HttpStatus.OK, "주문 상태가 성공적으로 변경되었습니다.", updatedPurchase)
    );
  }
}