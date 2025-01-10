package Not.Delivered.purchase.controller;


import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.domain.PurchaseStatus;
import Not.Delivered.purchase.dto.PurchaseDto;
import Not.Delivered.purchase.dto.PurchaseOfRiderDto;
import Not.Delivered.purchase.dto.PurchaseStatusUpdateDto;
import Not.Delivered.purchase.service.PurchaseService;
import Not.Delivered.user.domain.UserStatus;
import jakarta.validation.Valid;
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
  public ResponseEntity<ApiResponse<List<PurchaseOfRiderDto>>> getRiderPurchaseList(
      @RequestAttribute Long userId,
      @RequestAttribute UserStatus userStatus,
      @RequestParam(required = false) PurchaseStatus purchaseStatus) {

    List<PurchaseOfRiderDto> purchases = purchaseService.getPurchaseListForRider(userId, purchaseStatus);

    return ResponseEntity.ok(
        ApiResponse.success(HttpStatus.OK, "주문 목록 조회에 성공했습니다.", purchases)
    );
  }

  // 주문 상세 조회 - RIDER
  @GetMapping("/{purchaseId}")
  public ResponseEntity<ApiResponse<PurchaseOfRiderDto>> getRiderPurchase(
      @RequestAttribute Long userId,
      @RequestAttribute UserStatus userStatus,
      @PathVariable Long purchaseId) {

// 유저검증로직: 라이더

    PurchaseOfRiderDto purchase = purchaseService.getPurchaseForRider(userId, purchaseId);

    return ResponseEntity.ok(
        ApiResponse.success(HttpStatus.OK, "주문 조회에 성공했습니다.", purchase)
    );
  }

  // 주문 상태 변경 - RIDER (예: COOKED <-> DELIVERING <-> DELIVERED)
  @PatchMapping("/{purchaseId}")
  public ResponseEntity<ApiResponse<PurchaseOfRiderDto>> updateRiderPurchaseStatus(
      @RequestAttribute Long userId,
      @RequestAttribute UserStatus userStatus,
      @PathVariable Long purchaseId,
      @Valid @RequestBody PurchaseStatusUpdateDto request) {

// 유저검증로직: 라이더

    PurchaseStatus newStatus = request.getPurchaseStatus();

    PurchaseOfRiderDto updatedPurchase = purchaseService.updatePurchaseStatusByRider(userId, purchaseId, newStatus);

    return ResponseEntity.ok(
        ApiResponse.success(HttpStatus.OK, "주문 상태가 성공적으로 변경되었습니다.", updatedPurchase)
    );
  }
}