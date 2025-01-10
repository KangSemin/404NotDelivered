package Not.Delivered.purchase.controller;

import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.domain.PurchaseStatus;
import Not.Delivered.purchase.dto.PurchaseDto;
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
@RequestMapping("/owner/purchases")
public class PurchaseOwnerController {

  private final PurchaseService purchaseService;

  // 주문 목록 조회 - OWNER 자신의 가게에 대한 주문 조회
  @GetMapping
  public ResponseEntity<ApiResponse<List<PurchaseDto>>> getOwnerPurchaseList(
      @RequestAttribute Long userId,
      @RequestParam(required = false) PurchaseStatus purchaseStatus) {

    List<PurchaseDto> purchasesDto = purchaseService.getPurchaseListForOwner(userId,
        purchaseStatus);

    return ResponseEntity.ok(
        ApiResponse.success(HttpStatus.OK, "주문 목록 조회에 성공했습니다.", purchasesDto)
    );
  }

  // 주문 상세 조회 - OWNER 자신의 가게에 대한 주문 조회
  @GetMapping("/{purchaseId}")
  public ResponseEntity<ApiResponse<PurchaseDto>> getOwnerPurchase(
      @RequestAttribute Long userId,
      @PathVariable Long purchaseId) {

    PurchaseDto purchase = purchaseService.getPurchaseForOwner(userId, purchaseId);

    return ResponseEntity.ok(
        ApiResponse.success(HttpStatus.OK, "주문 조회에 성공했습니다.", purchase)
    );
  }

  // 주문 상태 변경 - OWNER (예: PENDING <-> COOKING <-> COOKED)
  @PatchMapping("/{purchaseId}")
  public ResponseEntity<ApiResponse<PurchaseDto>> updateOwnerPurchaseStatus(
      @RequestAttribute Long userId,
      @PathVariable Long purchaseId,
      @Valid @RequestBody PurchaseStatusUpdateDto request) {


    PurchaseStatus newStatus = request.getPurchaseStatus();

    PurchaseDto updatedPurchaseDto = purchaseService.updatePurchaseStatusByOwner(userId, purchaseId,
        newStatus);

    return ResponseEntity.ok(
        ApiResponse.success(HttpStatus.OK, "주문 상태가 성공적으로 변경되었습니다.", updatedPurchaseDto)
    );
  }
}